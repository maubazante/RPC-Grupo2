package com.unla.soapsys.response; 



@jakarta.xml.bind.annotation.XmlRootElement(name = "SendFileResponse")
public class SendFileResponse {
    private boolean resultado; // Indica si el envío fue exitoso

    public boolean getResultado() {
        return resultado;
    }

    public void setResultado(boolean resultado) {
        this.resultado = resultado;
    }
}
