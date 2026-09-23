package br.com.oopisani.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class ObjectMapper {

    // Esse obj mapeia Entidade para DTO & DTO para Entidade
    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    // Método que faz PARSING de Entity para DTO e de DTO para entidade
    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    // Método que faz PARSING de lista de Entity para lista de DTO e de lista de DTO para lista de entidade
    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
      // Criar uma lista temporária para a gente retornar.
      // É uma lista de objetos convertidos
        List<D> destinationObjects = new ArrayList<D>();
      // Iteramos sobre os objs originais
        for (Object o : origin) {
            destinationObjects.add(mapper.map(o,destination));
        }
        return destinationObjects;
    }


}
