package com.example.data.model

data class ShubhMuhurat(
    val name: String,
    val nameEn: String,
    val timeRange: String,
    val isAuspicious: Boolean, // true for Shubh, false for Inauspicious like Rahukaal
    val description: String,
    val descriptionEn: String
)

data class Panchang(
    val dateDisplay: String,
    val dateDisplayEn: String,
    val tithi: String,
    val tithiEn: String,
    val tithiEnd: String,
    val paksha: String, // शुक्ल पक्ष / कृष्ण पक्ष
    val pakshaEn: String,
    val varDay: String, // वार
    val varDayEn: String,
    val nakshatra: String,
    val nakshatraEn: String,
    val yoga: String,
    val yogaEn: String,
    val karana: String,
    val karanaEn: String,
    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    val moonset: String,
    val moonSign: String,
    val moonSignEn: String,
    val sunSign: String,
    val sunSignEn: String,
    val vikramSamvat: String,
    val shakaSamvat: String,
    val ritu: String,
    val muhurats: List<ShubhMuhurat>
)

val SampleTodayPanchang = Panchang(
    dateDisplay = "सोमवार, 28 सितम्बर 2026",
    dateDisplayEn = "Monday, 28 September 2026",
    tithi = "द्वितीया (प्रातः 08:42 तक, तत्पश्चात तृतीया)",
    tithiEn = "Dwitiya (until 08:42 AM, followed by Tritiya)",
    tithiEnd = "08:42 AM",
    paksha = "शुक्ल पक्ष",
    pakshaEn = "Shukla Paksha",
    varDay = "सोमवार (भगवान शिव का प्रिय दिन)",
    varDayEn = "Monday (Dedicated to Lord Shiva)",
    nakshatra = "स्वाति (दोपहर 02:15 तक, तत्पश्चात विशाखा)",
    nakshatraEn = "Swati (until 02:15 PM, then Vishakha)",
    yoga = "हर्षण (शाम 06:10 तक, तत्पश्चात वज्र)",
    yogaEn = "Harshana (until 06:10 PM, then Vajra)",
    karana = "कौलव (प्रातः 08:42 तक, फिर तैतिल)",
    karanaEn = "Kaulava (then Taitila)",
    sunrise = "06:12 AM",
    sunset = "06:18 PM",
    moonrise = "07:45 AM",
    moonset = "08:10 PM",
    moonSign = "तुला",
    moonSignEn = "Tula (Libra)",
    sunSign = "कन्या",
    sunSignEn = "Kanya (Virgo)",
    vikramSamvat = "2083 (कालयुक्त)",
    shakaSamvat = "1948 (शुभकृत)",
    ritu = "शरद ऋतु (Autumn)",
    muhurats = listOf(
        ShubhMuhurat(
            name = "ब्रह्म मुहूर्त",
            nameEn = "Brahma Muhurat",
            timeRange = "04:36 AM - 05:24 AM",
            isAuspicious = true,
            description = "ध्यान, प्राणायाम एवं ईश्वर वंदना के लिए सर्वश्रेष्ठ काल।",
            descriptionEn = "Supreme time for meditation, pranayama and divine communion."
        ),
        ShubhMuhurat(
            name = "अभिजीत मुहूर्त",
            nameEn = "Abhijit Muhurat",
            timeRange = "11:48 AM - 12:36 PM",
            isAuspicious = true,
            description = "सर्वकार्य सिद्धिदायक। नए कार्य एवं पूजन के लिए सर्वोत्तम।",
            descriptionEn = "Highly auspicious for starting new ventures and worship."
        ),
        ShubhMuhurat(
            name = "अमृत काल",
            nameEn = "Amrit Kaal",
            timeRange = "02:40 PM - 04:15 PM",
            isAuspicious = true,
            description = "अमृत के समान फलदायी एवं शांतिदायक समय।",
            descriptionEn = "Nectarine auspicious period for all noble actions."
        ),
        ShubhMuhurat(
            name = "गोधूलि मुहूर्त",
            nameEn = "Godhuli Muhurat",
            timeRange = "06:08 PM - 06:32 PM",
            isAuspicious = true,
            description = "संध्या वंदन, दीप प्रज्वलन एवं आरती का पावन समय।",
            descriptionEn = "Sacred twilight time for evening aarti and diya lighting."
        ),
        ShubhMuhurat(
            name = "राहुकाल (त्याज्य)",
            nameEn = "Rahu Kaal (Avoid)",
            timeRange = "07:45 AM - 09:15 AM",
            isAuspicious = false,
            description = "अशुभ समय। इस अवधि में नए मांगलिक कार्य शुरू करने से बचें।",
            descriptionEn = "Inauspicious period. Avoid starting new tasks during this time."
        ),
        ShubhMuhurat(
            name = "गुलिक काल",
            nameEn = "Gulik Kaal",
            timeRange = "01:45 PM - 03:15 PM",
            isAuspicious = true,
            description = "स्थिर एवं दीर्घकालिक कार्यों के लिए शुभ समय।",
            descriptionEn = "Auspicious for stable, long-term activities."
        )
    )
)
