package com.blandino.demo_park_api.web.dto.mapper;


import com.blandino.demo_park_api.entity.Cliente;
import com.blandino.demo_park_api.web.dto.ClienteCreateDto;
import com.blandino.demo_park_api.web.dto.ClienteResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteMapper {

    //Converte Um dto Simples para um Usuario Mas complexo
    public static Cliente toCliente(ClienteCreateDto dto){

        return  new ModelMapper().map(dto,Cliente.class);
    }

    public  static ClienteResponseDto toDto(Cliente cliente){
        return new ModelMapper().map(cliente,ClienteResponseDto.class);
    }

}
