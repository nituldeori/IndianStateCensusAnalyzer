package censusanalyser;

import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusCSV> censusCSVList = null;
    List<IPL2019BattingRecordCSV> IPLBattingRecordList = null;
    List<IPL2019BowlingRecordCSV> IPLBowlingRecordList = null;

    private <E> Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CensusAnalyserException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            censusCSVList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
            return censusCSVList.size();

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.HEADER_MISSING_EXCEPTION);
        }
    }

    public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            return this.getCount(censusCSVIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.HEADER_MISSING_EXCEPTION);
        }
    }

    public int loadIPL2019BattingData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            IPLBattingRecordList = csvBuilder.getCSVFileList(reader, IPL2019BattingRecordCSV.class);
            return IPLBattingRecordList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.HEADER_MISSING_EXCEPTION);
        }
    }

    public int loadIPL2019BowlingData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            IPLBowlingRecordList = csvBuilder.getCSVFileList(reader, IPL2019BowlingRecordCSV.class);
            return IPLBowlingRecordList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.HEADER_MISSING_EXCEPTION);
        }
    }


    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEntries;

    }


    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusCSVList == null || censusCSVList.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
        this.sortStateName(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusCSVList);
        return sortedStateCensusJson;
    }

    private void sortStateName(Comparator<IndiaCensusCSV> censusComparator) {
        for (int i = 0; i < censusCSVList.size() - 1; i++) {
            for (int j = 0; j < censusCSVList.size() - i - 1; j++) {
                IndiaCensusCSV census1 = censusCSVList.get(j);
                IndiaCensusCSV census2 = censusCSVList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusCSVList.set(j, census2);
                    censusCSVList.set(j + 1, census1);
                }
            }
        }
    }

    private void sortBatting(Comparator<IPL2019BattingRecordCSV> censusComparator) {
        for (int i = 0; i < IPLBattingRecordList.size() - 1; i++) {
            for (int j = 0; j < IPLBattingRecordList.size() - i - 1; j++) {
                IPL2019BattingRecordCSV avg1 = IPLBattingRecordList.get(j);
                IPL2019BattingRecordCSV avg2 = IPLBattingRecordList.get(j + 1);
                if (censusComparator.compare(avg1, avg2) < 0) {
                    IPLBattingRecordList.set(j, avg2);
                    IPLBattingRecordList.set(j + 1, avg1);
                }
            }
        }
    }

    private void sortBowling(Comparator<IPL2019BowlingRecordCSV> censusComparator) {
        for (int i = 0; i < IPLBowlingRecordList.size() - 1; i++) {
            for (int j = 0; j < IPLBowlingRecordList.size() - i - 1; j++) {
                IPL2019BowlingRecordCSV avg1 = IPLBowlingRecordList.get(j);
                IPL2019BowlingRecordCSV avg2 = IPLBowlingRecordList.get(j + 1);
                if (censusComparator.compare(avg1, avg2) > 0) {
                    IPLBowlingRecordList.set(j, avg2);
                    IPLBowlingRecordList.set(j + 1, avg1);
                }
            }
        }
    }



    public String getBattingAverageWiseSortedData() throws CensusAnalyserException {
        if (IPLBattingRecordList == null || IPLBattingRecordList.size() == 0) {
            throw new CensusAnalyserException("No Batting Data", CensusAnalyserException.ExceptionType.NO_BATTING_DATA);
        }
        Comparator<IPL2019BattingRecordCSV> battingRecordCSVComparator = Comparator.comparing(battingRecordCSV -> battingRecordCSV.average);
        this.sortBatting(battingRecordCSVComparator);
        String sortedStateCensusJson = new Gson().toJson(IPLBattingRecordList);
        return sortedStateCensusJson;
    }

    public String getBattingStrikeRateWiseSortedData() throws CensusAnalyserException {
        if (IPLBattingRecordList == null || IPLBattingRecordList.size() == 0) {
            throw new CensusAnalyserException("No Batting Data", CensusAnalyserException.ExceptionType.NO_BATTING_DATA);
        }
        Comparator<IPL2019BattingRecordCSV> battingRecordCSVComparator = Comparator.comparing(battingRecordCSV -> battingRecordCSV.strikeRate);
        this.sortBatting(battingRecordCSVComparator);
        String sortedStateCensusJson = new Gson().toJson(IPLBattingRecordList);
        return sortedStateCensusJson;
    }

    public String getSixesWiseSortedData() throws CensusAnalyserException {
        if (IPLBattingRecordList == null || IPLBattingRecordList.size() == 0) {
            throw new CensusAnalyserException("No Batting Data", CensusAnalyserException.ExceptionType.NO_BATTING_DATA);
        }
        Comparator<IPL2019BattingRecordCSV> battingRecordCSVComparator = Comparator.comparing(battingRecordCSV -> battingRecordCSV.sixes);
        this.sortBatting(battingRecordCSVComparator);
        String sortedStateCensusJson = new Gson().toJson(IPLBattingRecordList);
        return sortedStateCensusJson;
    }

    public String getFoursWiseSortedData() throws CensusAnalyserException {
        if (IPLBattingRecordList == null || IPLBattingRecordList.size() == 0) {
            throw new CensusAnalyserException("No Batting Data", CensusAnalyserException.ExceptionType.NO_BATTING_DATA);
        }
        Comparator<IPL2019BattingRecordCSV> battingRecordCSVComparator = Comparator.comparing(battingRecordCSV -> battingRecordCSV.fours);
        this.sortBatting(battingRecordCSVComparator);
        String sortedStateCensusJson = new Gson().toJson(IPLBattingRecordList);
        return sortedStateCensusJson;
    }

    public String getBowlingAverageWiseSortedData() throws CensusAnalyserException {
        if (IPLBowlingRecordList == null || IPLBowlingRecordList.size() == 0) {
            throw new CensusAnalyserException("No Bowling Data", CensusAnalyserException.ExceptionType.NO_BATTING_DATA);
        }
        Comparator<IPL2019BowlingRecordCSV> bowlingRecordCSVComparator = Comparator.comparing(bowlingRecordCSV -> bowlingRecordCSV.average);
        this.sortBowling(bowlingRecordCSVComparator);
        String sortedStateCensusJson = new Gson().toJson(IPLBowlingRecordList);
        return sortedStateCensusJson;
    }
}


