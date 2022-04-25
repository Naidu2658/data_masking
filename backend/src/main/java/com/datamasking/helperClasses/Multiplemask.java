package com.datamasking.helperClasses;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Data
public class Multiplemask {
    ArrayList<AlgorithmItem> algorithms;

}
