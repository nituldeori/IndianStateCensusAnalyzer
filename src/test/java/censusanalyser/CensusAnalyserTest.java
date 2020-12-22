package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATECODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String IPL2019_MOSTRUNS_FILE_PATH = "./src/test/resources/IPL2019FactsheetMostRuns.csv";
    private static final String IPL2019_MOSTWICKETS_FILE_PATH = "./src/test/resources/IPL2019FactsheetMostWkts.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithIncorrectType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndianStateCode(INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_MISSING_EXCEPTION,e.type);
        }
    }


    @Test
    public void givenStateCodeCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndianStateCode(INDIA_STATECODE_CSV_FILE_PATH);
            Assert.assertEquals(37,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenStateCodeCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndianStateCode(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenStateCodeData_WithIncorrectType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(INDIA_STATECODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_MISSING_EXCEPTION,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult(){
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void givenIPL2019BattingDataCSVFileReturnsCorrectRecords(){
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIPL2019BattingData(IPL2019_MOSTRUNS_FILE_PATH);
            Assert.assertEquals(100,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIPL2019BowlingDataCSVFileReturnsCorrectRecords(){
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIPL2019BowlingData(IPL2019_MOSTWICKETS_FILE_PATH);
            Assert.assertEquals(99,numOfRecords);
        } catch (CensusAnalyserException e) { }

    }

    @Test
    public void givenIPL2019BattingRecors_WhenSortedOnBattingAverage_ShouldReturnSortedResult(){
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadIPL2019BattingData(IPL2019_MOSTRUNS_FILE_PATH);
            String sortedBattingAverageData = censusAnalyser.getBattingAverageWiseSortedData();
            IPL2019BattingRecordCSV[] battingRecordCSV = new Gson().fromJson(sortedBattingAverageData,IPL2019BattingRecordCSV[].class);
            Assert.assertEquals("MS Dhoni", battingRecordCSV[0].player);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void givenIPL2019BattingRecors_WhenSortedOnStrikeRates_ShouldReturnSortedResult(){
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadIPL2019BattingData(IPL2019_MOSTRUNS_FILE_PATH);
            String sortedBattingStrikeRateData = censusAnalyser.getBattingStrikeRateWiseSortedData();
            IPL2019BattingRecordCSV[] battingRecordCSV = new Gson().fromJson(sortedBattingStrikeRateData,IPL2019BattingRecordCSV[].class);
            Assert.assertEquals("Ishant Sharma", battingRecordCSV[0].player);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }

    }






}
