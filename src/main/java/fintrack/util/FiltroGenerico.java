package fintrack.util;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FiltroGenerico <T>{
    public List<T> aplicarFiltro(List<T> listaOriginal, Predicate<T> criterio) {
        if (listaOriginal == null || criterio == null) {
            return listaOriginal;
        }

        return listaOriginal.stream()
                .filter(criterio)
                .collect(Collectors.toList());
    }
}
