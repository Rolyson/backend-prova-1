package br.com.tech4me.prova.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tech4me.prova.model.Musica;
import br.com.tech4me.prova.repository.MusicaRepository;
import br.com.tech4me.prova.shared.MusicaDto;

@Service
public class MusicaServiceImpl implements MusicaService {

  @Autowired
  private MusicaRepository repositorio;

  ModelMapper mapper = new ModelMapper();

  @Override
  public MusicaDto criarMusica(MusicaDto musicaDto) {
    Musica musica = mapper.map(musicaDto, Musica.class);
    musica = repositorio.save(musica);
    MusicaDto dto = mapper.map(musica, MusicaDto.class);

    return dto;
  }

  @Override
  public List<MusicaDto> obterMusicas() {
    List<Musica> musicas = repositorio.findAll();
    List<MusicaDto> musicaDto = musicas.stream().map(m -> mapper.map(m, MusicaDto.class)).collect(Collectors.toList());

    return musicaDto;
  }

  @Override
  public Optional<MusicaDto> obterPorId(String id) {
    Optional<Musica> musica = repositorio.findById(id);

    if (musica.isPresent()) {
      MusicaDto musicaDto = mapper.map(musica.get(), MusicaDto.class);
      return Optional.of(musicaDto);
    }

    return Optional.empty();
  }

  @Override
  public MusicaDto atualizarMusica(String id, MusicaDto pessoaDto) {
    Musica musica = mapper.map(pessoaDto, Musica.class);

    musica.setId(id);
    musica = repositorio.save(musica);

    MusicaDto dto = mapper.map(musica, MusicaDto.class);

    return dto;
  }

  @Override
  public void deletarMusicaPorId(String id) {
    repositorio.deleteById(id);
  }

}
