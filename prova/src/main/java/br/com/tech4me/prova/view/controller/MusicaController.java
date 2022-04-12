package br.com.tech4me.prova.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tech4me.prova.service.MusicaService;
import br.com.tech4me.prova.shared.MusicaDto;
import br.com.tech4me.prova.view.model.MusicaRequest;
import br.com.tech4me.prova.view.model.MusicaResponse;

@RestController
@RequestMapping("/api/musicas")
public class MusicaController {

  @Autowired
  private MusicaService servico;

  ModelMapper mapper = new ModelMapper();

  @GetMapping
  public ResponseEntity<List<MusicaResponse>> obterMusicas() {
    List<MusicaDto> musicaDto = servico.obterMusicas();
    List<MusicaResponse> response = musicaDto.stream().map(m -> mapper.map(m, MusicaResponse.class))
        .collect(Collectors.toList());

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<MusicaResponse> criarMusica(@RequestBody @Valid MusicaRequest request) {
    MusicaDto musicaDto = mapper.map(request, MusicaDto.class);
    musicaDto = servico.criarMusica(musicaDto);
    MusicaResponse response = mapper.map(musicaDto, MusicaResponse.class);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<MusicaResponse> obterPorId(@PathVariable String id) {
    Optional<MusicaDto> musicaDto = servico.obterPorId(id);

    if (musicaDto.isPresent()) {
      MusicaResponse response = mapper.map(musicaDto.get(), MusicaResponse.class);
      return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<MusicaResponse> atualizarMusica(@PathVariable String id, @RequestBody MusicaRequest request) {
    MusicaDto musicaDto = mapper.map(request, MusicaDto.class);
    musicaDto = servico.atualizarMusica(id, musicaDto);
    MusicaResponse response = mapper.map(musicaDto, MusicaResponse.class);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<String> deletarMusicaPorId(@PathVariable String id) {
    servico.deletarMusicaPorId(id);
    return new ResponseEntity<>("Deletado com sucesso!", HttpStatus.OK);
  }

}
