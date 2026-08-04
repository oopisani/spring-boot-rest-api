package br.com.oopisani.controllers;

import br.com.oopisani.exception.UnsupportedMathOperationException;
import br.com.oopisani.utils.SimpleMath;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.oopisani.utils.MathUtils;


@RestController
// Mapeador de rotas. Diz para o Spring qual URL do navegador aciona determinado método ou classe.
// Aqui, significa que todas operações dessa classe
// Vão começar com /math.
@RequestMapping("/math")
public class MathController {
    SimpleMath math = new SimpleMath();

//     http://localhost:8080/math/sum/3/5
// Sinaliza ao Spring que essa URL recebe requisições HTTP
// Detalha o caminho da URL. {} -> Mostra onde tem Path Variable
@RequestMapping("/sum/{numberOne}/{numberTwo}")
public Double sum(
// Captura os valores dos Path Variables na URL e transfere para os parâmetros escolhidos neste método.
        @PathVariable ("numberOne") String numberOne,
        @PathVariable("numberTwo") String numberTwo
        )  throws Exception  {
    return math.sum(MathUtils.convertToDouble(numberOne),MathUtils.convertToDouble(numberTwo));
}

@RequestMapping("/sub/{numberOne}/{numberTwo}")
public Double sub(
        @PathVariable("numberOne") String numberOne,
        @PathVariable("numberTwo") String numberTwo
) throws UnsupportedMathOperationException
      {
    return math.subtraction(MathUtils.convertToDouble(numberOne),MathUtils.convertToDouble(numberTwo));
}

@RequestMapping("/div/{numberOne}/{numberTwo}")
    public Double div(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo
    ) throws UnsupportedMathOperationException {
        return math.division(MathUtils.convertToDouble(numberOne), MathUtils.convertToDouble(numberTwo));

    }

    @RequestMapping("/media/{numberOne}/{numberTwo}")
    public Double media(
            @PathVariable("numberOne") String numberOne
    ) throws UnsupportedMathOperationException {
        return math.squareRoot(MathUtils.convertToDouble(numberOne));
    }
    @RequestMapping("/multi/{numberOne}/{numberTwo}")
    public Double multi(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo
    ) throws UnsupportedMathOperationException {
        return math.multiplication(MathUtils.convertToDouble(numberOne),MathUtils.convertToDouble(numberTwo));

    }

}