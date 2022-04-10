package br.com.tech4me.prova.service;

import java.util.List;
import java.util.Optional;

import br.com.tech4me.prova.shared.MusicaDto;

public interface MusicaService {
  MusicaDto criarMusica(MusicaDto musicaDto);

  List<MusicaDto> obterMusicas();

  Optional<MusicaDto> obterPorId(String id);

  MusicaDto atualizarMusica(String id, MusicaDto musicaDto);

  void deletarMusicaPorId(String id);
}
