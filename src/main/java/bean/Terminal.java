package bean;


public class Terminal {
    private String terminalId;
    private String terminalType;
    private String serverIp;
    private int serverPort;
    private String outLogPath;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getOutLogPath() {
        return outLogPath;
    }

    public void setOutLogPath(String outLogPath) {
        this.outLogPath = outLogPath;
    }



    @Override
    public String toString() {
        return "TERMINAL:: terminalID="+this.terminalId+" type=" + this.terminalType + " Server IP=" + this.serverIp + " Server Port=" + this.serverPort +
                " OUTlogpath=" + this.outLogPath;}

}

