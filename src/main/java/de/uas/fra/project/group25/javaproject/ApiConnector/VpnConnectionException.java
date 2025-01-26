package de.uas.fra.project.group25.javaproject.ApiConnector;

public class VpnConnectionException extends Exception {
    public VpnConnectionException() {
        super("Not connected to VPN. Please connect and retry!");
    }
}
