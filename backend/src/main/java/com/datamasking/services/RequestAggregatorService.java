package com.datamasking.services;

import com.datamasking.helperClasses.Algorithm;
import com.datamasking.helperClasses.DataConfigurationReqestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestAggregatorService {
    public DataConfigurationReqestBody aggregator(DataConfigurationReqestBody dataConfigurationReqestBody)
    {
        ArrayList<Algorithm> algorithms = new ArrayList<>();
        Map<String, ArrayList<Algorithm>> mp = new HashMap<>();

        for (Algorithm algorithm: dataConfigurationReqestBody.getAlgorithms())
        {
            System.out.println(algorithm.getName()+"::"+algorithm.getParameters().toString());
            ArrayList<Algorithm> algorithms1;
            if (mp.get(algorithm.getName()+"::"+algorithm.getParameters().toString()) == null) {
                algorithms1 = new ArrayList<>();
            }
            else
            {
                algorithms1 = mp.get(algorithm.getName()+"::"+algorithm.getParameters().toString());
            }
            algorithms1.add(algorithm);
            mp.put(algorithm.getName()+"::"+algorithm.getParameters().toString(), algorithms1);
        }

        for (Map.Entry<String, ArrayList<Algorithm>> entry: mp.entrySet())
        {
            Algorithm algorithm = new Algorithm();
            ArrayList<String> paths = new ArrayList<>();
            for (Algorithm algorithm1: entry.getValue())
            {
                paths.add(algorithm1.getPaths().get(0));
            }
            algorithm.setName(entry.getValue().get(0).getName());
            algorithm.setPaths(paths);
            algorithm.setParameters(entry.getValue().get(0).getParameters());
            algorithms.add(algorithm);
        }
        DataConfigurationReqestBody dataConfigurationReqestBody1 = new DataConfigurationReqestBody();
        dataConfigurationReqestBody1.setConfigurationName(dataConfigurationReqestBody.getConfigurationName());
        dataConfigurationReqestBody1.setOutputFileName(dataConfigurationReqestBody.getOutputFileName());
        dataConfigurationReqestBody1.setAlgorithms(algorithms);
        return dataConfigurationReqestBody1;
    }
}
