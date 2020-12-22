package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IPL2019BattingRecordCSV {
    @CsvBindByName(column = "POS")
    public int pos;

    @CsvBindByName(column = "PLAYER")
    public String player;

    @CsvBindByName(column = "Mat")
    public int matches;

    @CsvBindByName(column = "Inns")
    public int innings;

    @CsvBindByName(column = "NO")
    public int no;

    @CsvBindByName(column = "Runs")
    public int runs;

    @CsvBindByName(column = "HS")
    public int highest;

    @CsvBindByName(column = "Avg")
    public double average;

    @CsvBindByName(column = "BF")
    public int ballFaced;

    @CsvBindByName(column = "SR")
    public double strikeRate;

    @CsvBindByName(column = "100")
    public int hundreds;

    @CsvBindByName(column = "50")
    public int fifties;

    @CsvBindByName(column = "4s")
    public int fours;

    @CsvBindByName(column = "6s")
    public int sixes;

    @Override
    public String toString() {
        return "IPL2019BattingRecords{" +
                "POS='" + pos + '\'' +
                ", PLAYER='" + player + '\'' +
                ", Mat='" + matches + '\'' +
                ", Inns='" + innings + '\'' +
                "NO='" + no + '\'' +
                ", Runs='" + runs + '\'' +
                ", HS='" + highest + '\'' +
                ", Avg='" + average + '\'' +
                "BF='" + ballFaced + '\'' +
                ", SR='" + strikeRate + '\'' +
                ", 100='" + hundreds + '\'' +
                ", 50='" + fifties + '\'' +
                ", 4s='" + fours + '\'' +
                ", 6s='" + sixes + '\'' +
                '}';
    }



}
