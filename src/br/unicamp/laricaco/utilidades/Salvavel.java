package br.unicamp.laricaco.utilidades;

import java.io.*;

public interface Salvavel {

    void salvar(DataOutputStream outputStream) throws IOException;

}
