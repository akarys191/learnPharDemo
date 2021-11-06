package com.pharm.demo.web.reader.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Remain {
    private Integer inPackage;
    private Integer inPeace;

    @Override
    public boolean equals(Object obj) {
        Remain inStockObj = (Remain) obj;
        return inPackage.equals(inStockObj.getInPackage())
                && inPeace.equals(inStockObj.getInPeace());
    }
}
