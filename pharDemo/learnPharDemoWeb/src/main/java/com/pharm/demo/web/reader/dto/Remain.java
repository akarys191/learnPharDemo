package com.pharm.demo.web.reader.dto;


import lombok.*;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Remain {
    private Integer inPackage;
    private Integer inPeace;

    @Override
    public int hashCode() {
        return Objects.hash(inPackage,inPeace);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (this.getClass() != obj.getClass()) { return false; }
        Remain inStockObj = (Remain) obj;
        return inPackage.equals(inStockObj.getInPackage())
                && inPeace.equals(inStockObj.getInPeace());
    }
}
