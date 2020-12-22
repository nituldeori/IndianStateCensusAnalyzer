package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IPL2019BowlingRecordCSV {
    @CsvBindByName(column = "POS")
    public int pos;

    @CsvBindByName(column = "PLAYER")
    public String player;

    @CsvBindByName(column = "Mat")
    public int matches;

    @CsvBindByName(column = "Inns")
    public int innings;

    @CsvBindByName(column = "Ov")
    public double overs;

    @CsvBindByName(column = "Runs")
    public int runs;

    @CsvBindByName(column = "Wkts")
    public int wickets;

    @CsvBindByName(column = "BBI")
    public int bbi;

    @CsvBindByName(column = "Avg")
    public double average;

    @CsvBindByName(column = "Econ")
    public double economy;

    @CsvBindByName(column = "SR")
    public double strikeRate;

    @CsvBindByName(column = "4w")
    public int fourWickets;

    @CsvBindByName(column = "5w")
    public int fiveWickets;

    @CsvBindByName(column = "6s")
    public int sixes;

}
