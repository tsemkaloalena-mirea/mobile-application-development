package com.example.travelcompanyapplication.db_controller.db_fillers;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.travelcompanyapplication.db_controller.db_contracts.GuideReaderContract;
import com.example.travelcompanyapplication.db_controller.db_helpers.GuideDBHelper;

public class GuideDBFiller {
    private GuideDBHelper mGuideDBHelper;
    private String[][] guideData = {
            {"Moscow, Russia", "Moscow is the capital and largest city of Russia. The city stands on the Moskva River in Central Russia, with a population estimated at 12.4 million residents within the city limits, over 17 million residents in the urban area, and over 20 million residents in the metropolitan area. The city covers an area of 2,511 square kilometers (970 sq mi), while the urban area covers 5,891 square kilometers (2,275 sq mi), and the metropolitan area covers over 26,000 square kilometers (10,000 sq mi). Moscow is among the world's largest cities; being the most populous city entirely in Europe, the largest urban and metropolitan area in Europe, and the largest city by land area on the European continent.", "https://www.tripadvisor.ru/Attractions-g298484-Activities-Moscow_Central_Russia.html"},
            {"Paris, France", "Paris is the capital and most populous city of France, with an estimated population of 2,165,423 residents in 2019 in an area of more than 105 km² (41 sq mi), making it the 34th most densely populated city in the world in 2020. Since the 17th century, Paris has been one of the world's major centres of finance, diplomacy, commerce, fashion, gastronomy, science, and arts, and has sometimes been referred to as the capital of the world. The City of Paris is the centre and seat of government of the region and province of Île-de-France, or Paris Region, with an estimated population of 12,997,058 in 2020, or about 18% of the population of France, making it in 2020 the second largest metropolitan area in the OECD, and 14th largest in the world in 2015. The Paris Region had a GDP of €709 billion ($808 billion) in 2017. According to the Economist Intelligence Unit Worldwide Cost of Living Survey, in 2021 Paris was the city with the second-highest cost of living in the world, tied with Singapore, and after Tel Aviv.", "https://www.tripadvisor.ru/Attractions-g187147-Activities-Paris_Ile_de_France.html"},
            {"Saint Petersburg, Russia", "Saint Petersburg, formerly known as Petrograd (1914–1924) and later Leningrad (1924–1991), is the second-largest city in Russia. It is situated on the Neva River, at the head of the Gulf of Finland on the Baltic Sea, with a population of roughly 5.4 million residents. Saint Petersburg is the fourth-most populous city in Europe, the most populous city on the Baltic Sea, as well as the world's northernmost city with over 1 million residents. As Russia's Imperial capital, and a historically strategic port, it is governed as a federal city.\n" +
                    "The city was founded by Tsar Peter the Great on 27 May 1703 on the site of a captured Swedish fortress, and was named after apostle Saint Peter. In Russia, Saint Petersburg is historically and culturally associated with the birth of the Russian Empire and Russia's entry into modern history as a European great power. It served as a capital of the Tsardom of Russia, and the subsequent Russian Empire, from 1713 to 1918 (being replaced by Moscow for a short period of time between 1728 and 1730). After the October Revolution in 1917, the Bolsheviks moved their government to Moscow.\n", "https://www.tripadvisor.ru/Attractions-g298507-Activities-St_Petersburg_Northwestern_District.html"},
            {"Rome, Italy", "Rome is the capital city of Italy. It is also the capital of the Lazio region, the centre of the Metropolitan City of Rome, and a special comune named Comune di Roma Capitale. With 2,860,009 residents in 1,285 km2 (496.1 sq mi), Rome is the country's most populated comune and the third most populous city in the European Union by population within city limits. The Metropolitan City of Rome, with a population of 4,355,725 residents, is the most populous metropolitan city in Italy. Its metropolitan area is the third-most populous within Italy. Rome is located in the central-western portion of the Italian Peninsula, within Lazio (Latium), along the shores of the Tiber. Vatican City (the smallest country in the world) is an independent country inside the city boundaries of Rome, the only existing example of a country within a city. Rome is often referred to as the City of Seven Hills due to its geographic location, and also as the \"Eternal City\". Rome is generally considered to be the \"cradle of Western Christian culture and civilization\", and the center of the Catholic Church.", "https://www.tripadvisor.ru/Attractions-g187791-Activities-Rome_Lazio.html"},
            {"Tomsk, Russia", "Tomsk is a city and the administrative center of Tomsk Oblast in Russia, located on the Tom River. The city's population was 524,669 (2010 Census); 487,838 (2002 Census); 501,963 (1989 Census).\n" +
                    "Tomsk is considered one of the oldest towns in Siberia. It celebrated its 410th anniversary in 2014. The city is a notable educational and scientific center with six state universities, over 100,000 students, and the oldest university in Siberia.\n", "https://www.tripadvisor.ru/Attractions-g665310-Activities-Tomsk_Tomsk_Oblast_Siberian_District.html"}
    };

    public GuideDBFiller(GuideDBHelper guideDBHelper) {
        mGuideDBHelper = guideDBHelper;
    }

    private void addRow(String[] data) {
        SQLiteDatabase database = mGuideDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GuideReaderContract.GuideEntry.CITY_TITLE, data[0]);
        values.put(GuideReaderContract.GuideEntry.DESCRIPTION, data[1]);
        values.put(GuideReaderContract.GuideEntry.LINK, data[2]);

        long newRowId = database.insert(
                GuideReaderContract.GuideEntry.TABLE_NAME,
                null,
                values);
    }

    public void fillDB() {
        for (int i = 0; i < guideData.length; i++) {
            addRow(guideData[i]);
        }
    }
}