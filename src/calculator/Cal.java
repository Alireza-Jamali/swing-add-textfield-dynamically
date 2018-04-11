package calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.awt.print.*;
import java.text.NumberFormat;

public class Cal {

    public static int factorFPercentage = 49;
    public static int factorBPercentage = 0;
    public static int factorEPercentage = 51;
    public static float factorCPercentage = 25.4f;
    public static float factorDPercentage = 4.6f;
    public static int factorAPercentage = 1;

    PrinterJob job = PrinterJob.getPrinterJob();
    String roundedContent;
    String rawContent;
    static String plusContent;
    BigDecimal factorAPercent;
    public BigDecimal factorA;
    public BigDecimal factorB;
    public BigDecimal factorC;
    public BigDecimal factorD;
    public BigDecimal factorE;
    public BigDecimal factorF;

    public String roundedFactorAForExcel;
    public String roundedFactorBForExcel;
    public String roundedFactorCForExcel;
    public String roundedFactorDForExcel;
    public String roundedFactorEForExcel;
    public String roundedFactorFForExcel;

    public BigDecimal rawFactorAForExcel;
    public BigDecimal rawFactorBForExcel;
    public BigDecimal rawFactorCForExcel;
    public BigDecimal rawFactorDForExcel;
    public BigDecimal rawFactorEForExcel;
    public BigDecimal rawFactorFForExcel;

    public String writeRounded() {

        rawFactorAForExcel = factorA;
        rawFactorBForExcel = factorB;
        rawFactorCForExcel = factorC;
        rawFactorDForExcel = factorD;
        rawFactorEForExcel = factorE;
        rawFactorFForExcel = factorF;

        factorAPercent = factorAPercent.setScale(0, RoundingMode.HALF_EVEN);
        factorB = factorB.setScale(0, RoundingMode.HALF_EVEN);
        factorA = factorA.setScale(0, RoundingMode.HALF_EVEN);
        factorC = factorC.setScale(0, RoundingMode.HALF_EVEN);
        factorD = factorD.setScale(0, RoundingMode.HALF_EVEN);
        factorE = factorE.setScale(0, RoundingMode.HALF_EVEN);
        factorF = factorF.setScale(0, RoundingMode.HALF_EVEN);

        roundedContent = "Factor C: " + factorC + "     ";
        roundedContent += "Factor E: " + factorE + "     ";
        roundedContent += "Factor D: " + factorD + "     ";
        roundedContent += "Factor F: " + factorF + "     ";
        roundedContent += "Factor A: " + factorA + "     ";
        roundedContent += "Factor B: " + factorB + "     ";

        return roundedContent;
    }

    public String writeRaw() {

        factorA = factorA.setScale(2, RoundingMode.HALF_EVEN);
        factorB = factorB.setScale(2, RoundingMode.HALF_EVEN);
        factorC = factorC.setScale(2, RoundingMode.HALF_EVEN);
        factorD = factorD.setScale(2, RoundingMode.HALF_EVEN);
        factorE = factorE.setScale(2, RoundingMode.HALF_EVEN);
        factorF = factorF.setScale(2, RoundingMode.HALF_EVEN);

        rawContent = "Factor C: " + factorC.setScale(2, RoundingMode.HALF_EVEN) + "     ";
        rawContent += "Factor E: " + factorE.setScale(2, RoundingMode.HALF_EVEN) + "     ";
        rawContent += "Factor D: " + factorD.setScale(2, RoundingMode.HALF_EVEN) + "     ";
        rawContent += "Factor F: " + factorF.setScale(2, RoundingMode.HALF_EVEN) + "     ";
        rawContent += "Factor A: " + factorA.setScale(2, RoundingMode.HALF_EVEN) + "     ";
        rawContent += "Factor B: " + factorB.setScale(2, RoundingMode.HALF_EVEN) + "     ";

        return rawContent;
    }

    void calculate(double num) {

        BigDecimal input = new BigDecimal(num);
        factorAPercent = input.multiply(new BigDecimal(factorAPercentage)).divide(new BigDecimal(10));
        factorB = input.multiply(new BigDecimal(factorBPercentage)).divide(new BigDecimal(10));
        BigDecimal creditSumPercent = factorAPercent.add(factorB);
        factorA = input.multiply(new BigDecimal(10)).subtract(creditSumPercent);
        factorC = factorAPercent.multiply(new BigDecimal(factorCPercentage)).divide(new BigDecimal(100));
        factorD = factorAPercent.multiply(new BigDecimal(factorDPercentage)).divide(new BigDecimal(100));
        BigDecimal vk = factorAPercent.subtract(factorC.add(factorD));
        factorE = vk.multiply(new BigDecimal(factorEPercentage)).divide(new BigDecimal(100));
        factorF = vk.multiply(new BigDecimal(factorFPercentage)).divide(new BigDecimal(100));

    }

    String singleFactorCalculate(double num) {

        String output = "";
        BigDecimal input = new BigDecimal(num);
        BigDecimal factorAPercent = input.multiply(new BigDecimal(factorAPercentage)).divide(new BigDecimal(10));
        BigDecimal factorBPercent = input.multiply(new BigDecimal(factorBPercentage)).divide(new BigDecimal(10));
        BigDecimal factorBSumPercent = factorAPercent.add(factorBPercent);
        BigDecimal factorA = input.multiply(new BigDecimal(10)).subtract(factorBSumPercent);
        BigDecimal factorC = factorAPercent.multiply(new BigDecimal(factorCPercentage)).divide(new BigDecimal(100));
        BigDecimal factorD = factorAPercent.multiply(new BigDecimal(factorDPercentage)).divide(new BigDecimal(100));
        BigDecimal EF = factorAPercent.subtract(factorC.add(factorD));
        BigDecimal factorE = EF.multiply(new BigDecimal(factorEPercentage)).divide(new BigDecimal(100));
        BigDecimal factorF = EF.multiply(new BigDecimal(factorFPercentage)).divide(new BigDecimal(100));

        factorA = factorA.setScale(0, RoundingMode.HALF_EVEN);
        factorBPercent = factorBPercent.setScale(0, RoundingMode.HALF_EVEN);
        factorC = factorC.setScale(0, RoundingMode.HALF_EVEN);
        factorD = factorD.setScale(0, RoundingMode.HALF_EVEN);
        factorE = factorE.setScale(0, RoundingMode.HALF_EVEN);
        factorF = factorF.setScale(0, RoundingMode.HALF_EVEN);

        output = "Factor C: " + factorC + "     ";
        output += "Factor E: " + factorE + "     ";
        output += "Factor D: " + factorD + "     ";
        output += "Factor F: " + factorF + "     ";
        output += "Factor A: " + factorA + "     ";
        output += "Factor B: " + factorBPercent + "     ";

        return output;
    }

    //calculate and roundup
    public void singleFactorCalculateForExcel(double num) {

        BigDecimal input = new BigDecimal(num);
        BigDecimal factorAPercent = input.multiply(new BigDecimal(factorAPercentage)).divide(new BigDecimal(10));
        BigDecimal factorB = input.multiply(new BigDecimal(factorBPercentage)).divide(new BigDecimal(10));
        BigDecimal factorBSumPercent = factorAPercent.add(factorB);
        BigDecimal factorA = input.multiply(new BigDecimal(10)).subtract(factorBSumPercent);
        BigDecimal factorC = factorAPercent.multiply(new BigDecimal(factorCPercentage)).divide(new BigDecimal(100));
        BigDecimal factorD = factorAPercent.multiply(new BigDecimal(factorDPercentage)).divide(new BigDecimal(100));
        BigDecimal EF = factorAPercent.subtract(factorC.add(factorD));
        BigDecimal factorE = EF.multiply(new BigDecimal(factorEPercentage)).divide(new BigDecimal(100));
        BigDecimal factorF = EF.multiply(new BigDecimal(factorFPercentage)).divide(new BigDecimal(100));

        factorA = factorA.setScale(0, RoundingMode.HALF_EVEN);
        factorB = factorB.setScale(0, RoundingMode.HALF_EVEN);
        factorC = factorC.setScale(0, RoundingMode.HALF_EVEN);
        factorD = factorD.setScale(0, RoundingMode.HALF_EVEN);
        factorE = factorE.setScale(0, RoundingMode.HALF_EVEN);
        factorF = factorF.setScale(0, RoundingMode.HALF_EVEN);

        roundedFactorAForExcel = factorA.toString().replaceAll("(\\d)(?=(\\d{3})+$)", "$1,");
        roundedFactorBForExcel = factorB.toString().replaceAll("(\\d)(?=(\\d{3})+$)", "$1,");
        roundedFactorCForExcel = factorC.toString().replaceAll("(\\d)(?=(\\d{3})+$)", "$1,");
        roundedFactorDForExcel = factorD.toString().replaceAll("(\\d)(?=(\\d{3})+$)", "$1,");
        roundedFactorEForExcel = factorE.toString().replaceAll("(\\d)(?=(\\d{3})+$)", "$1,");
        roundedFactorFForExcel = factorF.toString().replaceAll("(\\d)(?=(\\d{3})+$)", "$1,");
    }
}
