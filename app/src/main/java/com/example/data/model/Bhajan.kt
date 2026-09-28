package com.example.data.model

data class Bhajan(
    val id: String,
    val title: String,
    val titleEn: String,
    val deity: String,
    val deityEn: String,
    val durationText: String,
    val durationSeconds: Int,
    val category: BhajanCategory,
    val lyrics: String,
    val artistOrTradition: String,
    val baseFrequencyHz: Float = 216.0f // Devotional drone root tone
)

enum class BhajanCategory(val hindiName: String, val englishName: String) {
    ALL("सभी", "All"),
    MORNING("सुबह के भजन", "Morning Bhajans"),
    AARTI("आरती", "Aarti"),
    KIRTAN("कीर्तन", "Kirtan"),
    MANTRA("मंत्र", "Mantra"),
    FAVORITES("मेरे पसंदीदा", "Favorites")
}

val SampleBhajans = listOf(
    Bhajan(
        id = "bhajan_jagdish",
        title = "ॐ जय जगदीश हरे",
        titleEn = "Om Jai Jagdish Hare",
        deity = "श्री कृष्ण / विष्णु",
        deityEn = "Shri Krishna / Vishnu",
        durationText = "05:42",
        durationSeconds = 342,
        category = BhajanCategory.AARTI,
        lyrics = """
ॐ जय जगदीश हरे, स्वामी जय जगदीश हरे।
भक्त जनों के संकट, दास जनों के संकट, क्षण में दूर करे॥ ॐ जय जगदीश हरे॥

जो ध्यावे फल पावे, दुःख बिनसे मन का।
सुख सम्पति घर आवे, कष्ट मिटे तन का॥ ॐ जय जगदीश हरे॥

मात पिता तुम मेरे, शरण गहूं किसकी।
तुम बिन और न दूजा, प्रभु बिन और न दूजा, आस करूं जिसकी॥ ॐ जय जगदीश हरे॥

तुम पूरण परमात्मा, तुम अन्तर्यामी।
पारब्रह्म परमेश्वर, तुम सब के स्वामी॥ ॐ जय जगदीश हरे॥
        """.trimIndent(),
        artistOrTradition = "पारंपरिक आरती",
        baseFrequencyHz = 220.0f
    ),
    Bhajan(
        id = "bhajan_amritwani",
        title = "शिव अमृतवाणी",
        titleEn = "Shiv Amritwani",
        deity = "शिव जी",
        deityEn = "Lord Shiva",
        durationText = "07:15",
        durationSeconds = 435,
        category = BhajanCategory.MORNING,
        lyrics = """
जय शिव शंकर, जय त्रिपुरारी।
दुःख भंजन, सब संकट हारी॥

भोलेनाथ दया के सागर,
करुणा निधान, सब सुख आगर॥

शीश गंग, गल मुंड माल,
बाघम्बर तन सोहे विशाल॥

त्रिशूल डमरू कर में विराजे,
मस्तक पर चंदा चमकाजे॥
        """.trimIndent(),
        artistOrTradition = "महाकाल भक्ति",
        baseFrequencyHz = 196.0f
    ),
    Bhajan(
        id = "bhajan_chalisa",
        title = "श्री हनुमान चालीसा",
        titleEn = "Shri Hanuman Chalisa",
        deity = "हनुमान जी",
        deityEn = "Hanuman Ji",
        durationText = "09:30",
        durationSeconds = 570,
        category = BhajanCategory.MORNING,
        lyrics = """
दोहा:
श्रीगुरु चरन सरोज रज, निज मनु मुकुरु सुधारि।
बरनऊं रघुबर बिमल जसु, जो दायकु फल चारि॥

बुद्धिहीन तनु जानिके, सुमिरौं पवन-कुमार।
बल बुद्धि बिद्या देहु मोहिं, हरहु कलेस बिकार॥

चौपाई:
जय हनुमान ज्ञान गुन सागर। जय कपीस तिहुं लोक उजागर॥
राम दूत अतुलित बल धामा। अंजनि-पुत्र पवनसुत नामा॥
महाबीर बिक्रम बजरंगी। कुमति निवार सुमति के संगी॥
कंचन बरन बिराज सुबेसा। कानन कुंडल कुंचित केसा॥
        """.trimIndent(),
        artistOrTradition = "गोस्वामी तुलसीदास जी",
        baseFrequencyHz = 216.0f
    ),
    Bhajan(
        id = "bhajan_durga",
        title = "जय माँ दुर्गे - जगदम्बे",
        titleEn = "Jai Maa Durge Jagdambe",
        deity = "माता रानी",
        deityEn = "Mata Rani",
        durationText = "06:18",
        durationSeconds = 378,
        category = BhajanCategory.AARTI,
        lyrics = """
जय अम्बे गौरी, मैया जय श्यामा गौरी।
तुमको निशदिन ध्यावत, हरि ब्रह्मा शिवरी॥ जय अम्बे गौरी॥

मांग सिंदूर विराजत, टीको मृगमद को।
उज्ज्वल से दोउ नैना, चंद्रवदन नीको॥ जय अम्बे गौरी॥

कनक समान कलेवर, रक्ताम्बर राजे।
रक्तपुष्प गल माला, कंठन पर साजे॥ जय अम्बे गौरी॥
        """.trimIndent(),
        artistOrTradition = "देवी स्तुति",
        baseFrequencyHz = 240.0f
    ),
    Bhajan(
        id = "bhajan_ganesh",
        title = "गणेश आरती - जय गणेश देवा",
        titleEn = "Ganesh Aarti - Jai Ganesh Deva",
        deity = "गणेश जी",
        deityEn = "Ganesh Ji",
        durationText = "04:50",
        durationSeconds = 290,
        category = BhajanCategory.AARTI,
        lyrics = """
जय गणेश, जय गणेश, जय गणेश देवा।
माता जाकी पार्वती, पिता महादेवा॥

एक दंत दयावंत, चार भुजा धारी।
माथे सिंदूर सोहे, मूसे की सवारी॥

पान चढ़े, फूल चढ़े, और चढ़े मेवा।
लड्डुअन का भोग लगे, संत करें सेवा॥

दीनन की लाज राखो, शंभु सुत वारी।
कामना को पूर्ण करो, जग बलिहारी॥
        """.trimIndent(),
        artistOrTradition = "विघ्नहर्ता प्रार्थना",
        baseFrequencyHz = 261.6f
    ),
    Bhajan(
        id = "bhajan_achyutam",
        title = "अच्युतम केशवम कृष्ण दामोदरम",
        titleEn = "Achyutam Keshavam",
        deity = "श्री कृष्ण जी",
        deityEn = "Lord Krishna",
        durationText = "05:10",
        durationSeconds = 310,
        category = BhajanCategory.KIRTAN,
        lyrics = """
अच्युतं केशवं कृष्ण दामोदरं,
राम नारायणं जानकी वल्लभम्।

कौन कहता है भगवान आते नहीं,
तुम मीरा के जैसे बुलाते नहीं॥

कौन कहता है भगवान खाते नहीं,
बेर शबरी के जैसे खिलाते नहीं॥

अच्युतं केशवं कृष्ण दामोदरं,
राम नारायणं जानकी वल्लभम्॥
        """.trimIndent(),
        artistOrTradition = "मधुर संकीर्तन",
        baseFrequencyHz = 220.0f
    ),
    Bhajan(
        id = "bhajan_gayatri",
        title = "गायत्री महामंत्र (११ जप)",
        titleEn = "Gayatri Mahamantra Chants",
        deity = "सूर्य देव / वेद माता",
        deityEn = "Surya Dev / Gayatri Mata",
        durationText = "08:00",
        durationSeconds = 480,
        category = BhajanCategory.MANTRA,
        lyrics = """
ॐ भूर्भुवः स्वः
तत्सवितुर्वरेण्यं
भर्गो देवस्य धीमहि
धियो यो नः प्रचोदयात्॥

अर्थ:
उस प्राणस्वरूप, दुःखनाशक, सुखस्वरूप, श्रेष्ठ, तेजस्वी, पापनाशक,
देवस्वरूप परमात्मा को हम अन्तःकरण में धारण करें।
वह परमात्मा हमारी बुद्धि को सन्मार्ग में प्रेरित करे।
        """.trimIndent(),
        artistOrTradition = "ऋग्वैदिक पावन मंत्र",
        baseFrequencyHz = 216.0f
    ),
    Bhajan(
        id = "bhajan_ramstuti",
        title = "श्री राम स्तुति - श्री रामचंद्र कृपालु",
        titleEn = "Shri Ram Stuti",
        deity = "श्री राम जी",
        deityEn = "Lord Shri Ram",
        durationText = "05:55",
        durationSeconds = 355,
        category = BhajanCategory.MORNING,
        lyrics = """
श्रीरामचन्द्र कृपालु भजु मन हरण भवभय दारुणं।
नवकंज लोचन, कंज मुख, कर कंज, पद कंजारुणं॥

कन्दर्प अगणित अमित छबि, नवनील नीरद सुन्दरं।
पट पीत मानहु तड़ित रुचि शुचि नौमि जनक सुतावरं॥

भजु दीनबन्धु दिनेश दानव दैत्यवंश निकन्दनं।
रघुनन्द आनन्दकन्द कोशलचन्द दशरथ नन्दनं॥
        """.trimIndent(),
        artistOrTradition = "गोस्वामी तुलसीदास",
        baseFrequencyHz = 216.0f
    ),
    Bhajan(
        id = "bhajan_radhe",
        title = "राधे राधे जपो चले आएंगे बिहारी",
        titleEn = "Radhe Radhe Japo",
        deity = "राधा रानी / श्री कृष्ण",
        deityEn = "Radha Rani / Krishna",
        durationText = "06:40",
        durationSeconds = 400,
        category = BhajanCategory.KIRTAN,
        lyrics = """
राधे राधे जपो चले आएंगे बिहारी।
आएंगे बिहारी चले आएंगे बिहारी॥

राधा मेरी स्वामिनी, मैं राधे को दास।
जनम जनम मोहे दीजियो, श्री वृन्दावन वास॥

राधे राधे कीर्तन से मन निर्मल हो जाए,
मोहन मुरलीधर स्वयं कृपा बरसाएं॥
        """.trimIndent(),
        artistOrTradition = "वृन्दावन धाम कीर्तन",
        baseFrequencyHz = 220.0f
    ),
    Bhajan(
        id = "bhajan_mahamrityunjaya",
        title = "महामृत्युंजय मंत्र जप",
        titleEn = "Mahamrityunjaya Mantra",
        deity = "शिव जी",
        deityEn = "Lord Shiva",
        durationText = "10:08",
        durationSeconds = 608,
        category = BhajanCategory.MANTRA,
        lyrics = """
ॐ त्र्यम्बकं यजामहे सुगन्धिं पुष्टिवर्धनम्।
उर्वारुकमिव बन्धनान्मृत्योर्मुक्षीय मामृतात्॥

अर्थ:
हम त्रिनेत्रधारी भगवान शिव की आराधना करते हैं, जो प्रत्येक श्वास में जीवन शक्ति का पोषण करते हैं।
जिस प्रकार फल पक कर डाली के बंधन से मुक्त हो जाता है,
उसी प्रकार हमें भी मृत्यु व अज्ञान के भय से मुक्त कर अमरता का वरदान दें।
        """.trimIndent(),
        artistOrTradition = "यजुर्वेदीय महामंत्र",
        baseFrequencyHz = 196.0f
    )
)
