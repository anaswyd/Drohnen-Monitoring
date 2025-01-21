package de.uas.fra.project.group25.javaproject.ApiConnector;

public class TestTreiber {

    /**
     * main for debugging API
     *
     */
    public static void main(String[] args) {

        //System.out.println("===========DRONE TABLE=============");
      // Drone List - OutputDroneTable

        ApiAccess apiAccess = new ApiAccess();
        System.out.println(apiAccess.getOutputCatalogue());



    }
}
