package com.capstone.service;

import com.capstone.domain.Gender;
import com.capstone.domain.Item.Bottom;
import com.capstone.domain.Item.Item;
import com.capstone.domain.Item.Top;
import com.capstone.repository.ItemRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InitDBService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveBottomCSV() {
        try {
            ClassPathResource resource = new ClassPathResource("/static/itemCSV.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), Charset.forName("x-windows-949")));
            CSVReader csvReader = new CSVReader(reader);

            List<String[]> records = csvReader.readAll();

            for (int i = 1; i < records.size(); i++) {
                String[] record = records.get(i);

                String dtype = record[2];
                Item item;

                if ("하의".equals(dtype)) {
                    item = new Bottom();
                    ((Bottom) item).setLength(Double.parseDouble(record[4]));
                    ((Bottom) item).setWaistWidth(Double.parseDouble(record[5]));
                }
                else {
                    continue;
                }

                item.setCompany(record[0]);
                item.setItemName(record[1]);
                item.setSize(record[3]);
                item.setPrice(record[7]);
                item.setSiteUrl(record[9]);
                item.setImage(record[8]);

                String genderStr = record[6];
                Gender gender = null;
                switch (genderStr) {
                    case "남성":
                        gender = Gender.MALE;
                        break;
                    case "여성":
                        gender = Gender.FEMALE;
                        break;
                    case "공용":
                        gender = Gender.FREE;
                        break;
                }
                item.setItemGender(gender);

                itemRepository.save(item);
            }
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }


    @Transactional
    public void saveTopCSV() {
        try {
            ClassPathResource resource = new ClassPathResource("/static/기캡상의.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            CSVReader csvReader = new CSVReader(reader);

            List<String[]> records = csvReader.readAll();

            for (int i = 1; i < records.size(); i++) {
                String[] record = records.get(i);

                String dtype = record[1];
                Item item;

                if ("상의".equals(dtype)) {
                    item = new Top();
                    ((Top) item).setLength(Double.parseDouble(record[3]));
                    ((Top) item).setShoulderWidth(Double.parseDouble(record[4]));
                    ((Top) item).setChestCrossSection(Double.parseDouble(record[5]));
                    ((Top) item).setSleeveLength(Double.parseDouble(record[6]));
                } else {
                    continue;
                }

                item.setCompany(record[9]);
                item.setItemName(record[0]);
                item.setSize(record[2]);
                item.setPrice(record[8]);
                item.setSiteUrl(record[11]);
                item.setImage(record[7]);

                String genderStr = record[10];
                Gender gender = null;
                switch (genderStr) {
                    case "남성":
                        gender = Gender.MALE;
                        break;
                    case "여성":
                        gender = Gender.FEMALE;
                        break;
                    case "공용":
                        gender = Gender.FREE;
                        break;
                }
                item.setItemGender(gender);

                itemRepository.save(item);
            }
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }
}
