package com.example.practica5.application.step;

import com.example.practica5.application.model.Calle;
import com.example.practica5.application.model.CalleCsv;
import com.example.practica5.application.utils.ErrorLogWriter;
import org.springframework.batch.infrastructure.item.ItemProcessor;

public class CalleItemProcessor implements ItemProcessor<CalleCsv, Calle> {
    private String distritoFiltro;
    private final ErrorLogWriter errorLogWriter = new ErrorLogWriter();

    public CalleItemProcessor(String distritoFiltro) {
        this.distritoFiltro = distritoFiltro;
    }

    @Override
    public Calle process(CalleCsv item) {
        try {
            if(!item.getNomDistrito().equals(distritoFiltro)){
                return null;
            }

            Calle calle = new Calle();

            calle.setCodigoCalle(Integer.parseInt(item.getCodigoCalle()));
            calle.setTipoVia(item.getTipoVia());
            calle.setNombreCalle(item.getNombreCalle());
            calle.setPrimerNumTramo(Integer.parseInt(item.getPrimerNumTramo()));
            calle.setUltimoNumTramo(Integer.parseInt(item.getUltimoNumTramo()));
            calle.setBarrio(item.getBarrio());
            calle.setCodDistrito(Integer.parseInt(item.getCodDistrito()));
            calle.setNomDistrito(item.getNomDistrito());

            return calle;

        } catch (Exception e) {
            errorLogWriter.writeError("Error procesando registro: " + item + " | Motivo: " + e.getMessage());
            return null;
        }
    }
}
