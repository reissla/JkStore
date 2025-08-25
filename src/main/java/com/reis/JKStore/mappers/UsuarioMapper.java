package com.reis.JKStore.mappers;

import com.reis.JKStore.domain.Usuario;
import com.reis.JKStore.domain.dtos.UsuarioCreateDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    Usuario UsuarioCreateDtoToUsuario(UsuarioCreateDto dto);
}
