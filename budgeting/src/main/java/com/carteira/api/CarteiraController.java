package com.carteira.api;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/carteira")
public class CarteiraController {

    private final TransacaoRepository repository;
    private final GeminiService geminiService;

    public CarteiraController(TransacaoRepository repository, GeminiService geminiService) {
        this.repository = repository;
        this.geminiService = geminiService;
    }

    // Endpoint 1: Enviar áudio para salvar despesa via IA
    @PostMapping("/ia")
    public ResponseEntity<?> cadastrarViaAudio(@RequestParam("audio") MultipartFile file) {
        try {
            if (file.isEmpty()) return ResponseEntity.badRequest().body("Arquivo de áudio vazio.");
            
            // Envia o áudio direto para o Gemini processar
            String resultadoIA = geminiService.processarAudio(file.getBytes(), file.getContentType());
            
            // Processa o JSON retornado pela IA
            JSONObject json = new JSONObject(resultadoIA);
            
            Transacao novaTransacao = new Transacao(
                json.getString("descricao"),
                json.getBigDecimal("valor"),
                json.getString("categoria").toUpperCase()
            );

            repository.save(novaTransacao);
            return ResponseEntity.ok(novaTransacao);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao processar: " + e.getMessage());
        }
    }

    // Endpoint 2: Buscar todas as despesas salvas
    @GetMapping
    public List<Transacao> listarTodas() {
        return repository.findAll();
    }

    // Endpoint 3: Buscar por categoria específica
    @GetMapping("/categoria/{nome}")
    public List<Transacao> listarPorCategoria(@PathVariable String nome) {
        return repository.findByCategoriaIgnoreCase(nome);
    }
}
