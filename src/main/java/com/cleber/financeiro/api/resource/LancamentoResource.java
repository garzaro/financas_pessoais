package com.cleber.financeiro.api.resource;

import com.cleber.financeiro.api.dto.LancamentoDTO;
import com.cleber.financeiro.exception.RegraDeNegocioException;
import com.cleber.financeiro.model.entity.Lancamento;
import com.cleber.financeiro.model.entity.StatusLancamento;
import com.cleber.financeiro.model.entity.TipoLancamento;
import com.cleber.financeiro.model.entity.Usuario;
import com.cleber.financeiro.service.LancamentoService;
import com.cleber.financeiro.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoResource {
    
    private LancamentoService lancamentoService;
    
    private UsuarioService usuarioService;
    
    public LancamentoResource(LancamentoService lancamentoService){
        this.lancamentoService = lancamentoService;
    }
    
    @PostMapping
    public ResponseEntity salvarLancamento(@RequestBody LancamentoDTO dto){
        try {
            Lancamento converteEntidade = converterDtoParaEntidade(dto);
            converteEntidade = lancamentoService.salvarLancamento(converteEntidade);
            return new ResponseEntity(converteEntidade, HttpStatus.CREATED);
            
        }catch (RegraDeNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("{id}")
    public ResponseEntity atualizarLancamento(@PathVariable("id") Long id,  @RequestBody LancamentoDTO dto){

        return lancamentoService.obterLancamentoPorId(id).map(entity ->{
            try {
                Lancamento lancamento = converterDtoParaEntidade(dto);
                lancamento.setId(entity.getId());
                lancamentoService.atualizarLancamento(lancamento);
                return ResponseEntity.ok(lancamento);
            }catch (RegraDeNegocioException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() -> new ResponseEntity("Lancamento não encontrado", HttpStatus.BAD_REQUEST));
    }
    
    @GetMapping
    public ResponseEntity buscar(
    		@RequestParam(value = "descricao", required = false) String descricao,
    		@RequestParam(value= "mes", required = false) Integer mes,
    		@RequestParam(value= "ano", required = false) Integer ano,
    		@RequestParam("usuario") Long idusuario
    		) {
      
    	Lancamento lancamentoFiltro = new Lancamento();
    	lancamentoFiltro.setDescricao(descricao);
        lancamentoFiltro.setMes(mes);
        lancamentoFiltro.setAno(ano);
        
        Optional<Usuario> usuario = usuarioService.obterUsuarioPorId(idusuario);
        
        if (usuario.isPresent()) {
            return ResponseEntity.badRequest().body("Consulta não realizada, o usuario não existe");
        }else {
            lancamentoFiltro.setUsuario(usuario.get());
        }
        
        List<Lancamento> lancamentos = lancamentoService.buscarLancamento(lancamentoFiltro);
        return ResponseEntity.ok(lancamentos);
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable ("id") Long id){
        return lancamentoService.obterLancamentoPorId(id).map(entity ->{
            lancamentoService.deletarLancamento(entity);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet( ()->
                new ResponseEntity("Lançamento não encontrado na base de dados", HttpStatus.BAD_REQUEST) );
    }
    
    private Lancamento converterDtoParaEntidade(LancamentoDTO dto){
        Lancamento lancamento = new Lancamento();
        lancamento.setId(dto.getId());
        lancamento.setDescricao(dto.getDescricao());
        lancamento.setAno(dto.getAno());
        lancamento.setMes(dto.getMes());
        lancamento.setValor(dto.getValor());
        
        Usuario buscarUsuario = usuarioService.obterUsuarioPorId(dto.getUsuario())
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado com o id informado"));

        lancamento.setUsuario(buscarUsuario);
        lancamento.setTipoLancamento(TipoLancamento.valueOf(dto.getTipo()));
        lancamento.setStatusLancamento(StatusLancamento.valueOf(dto.getStatus()));
        return lancamento;
    }
}
