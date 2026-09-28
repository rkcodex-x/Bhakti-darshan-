package com.example.data.model

data class Suvichar(
    val id: String,
    val quote: String,
    val quoteEn: String,
    val authorOrSource: String,
    val authorOrSourceEn: String,
    val contextTag: String
)

val SampleSuvichars = listOf(
    Suvichar(
        id = "suvichar_1",
        quote = "कर्मण्येवाधिकारस्ते मा फलेषु कदाचन। मा कर्मफलहेतुर्भूर्मा ते सङ्गोऽस्त्वकर्मणि॥",
        quoteEn = "You have a right to perform your prescribed duty, but you are not entitled to the fruits of action.",
        authorOrSource = "श्रीमद्भगवद्गीता (अध्याय 2, श्लोक 47)",
        authorOrSourceEn = "Bhagavad Gita (2.47)",
        contextTag = "कर्मयोग"
    ),
    Suvichar(
        id = "suvichar_2",
        quote = "जब मन में ईश्वर का वास होता है, तो भय, चिंता और निराशा स्वतः ही समाप्त हो जाती हैं। हर सुबह परमात्मा को समर्पित करें।",
        quoteEn = "When the Divine resides in your heart, fear, worry and anxiety naturally dissolve. Dedicate every morning to God.",
        authorOrSource = "भक्ति अमृत",
        authorOrSourceEn = "Bhakti Wisdom",
        contextTag = "शांति"
    ),
    Suvichar(
        id = "suvichar_3",
        quote = "गुरु गोबिंद दोऊ खड़े, काके लागूं पांय। बलिहारी गुरु आपनो, गोबिंद दियो बताय॥",
        quoteEn = "Both Guru and God are standing before me; to whom should I bow first? Glory to the Guru who showed me the path to God.",
        authorOrSource = "संत कबीर दास",
        authorOrSourceEn = "Sant Kabir Das",
        contextTag = "गुरु वंदना"
    ),
    Suvichar(
        id = "suvichar_4",
        quote = "जाकी रही भावना जैसी, प्रभु मूरति देखी तिन तैसी। शुद्ध भाव ही परमात्मा की प्राप्ति का सरलतम मार्ग है।",
        quoteEn = "According to one's devotional purity, one perceives the divine image. Pure devotion is the simplest path to the Divine.",
        authorOrSource = "श्रीरामचरितमानस (गोस्वामी तुलसीदास)",
        authorOrSourceEn = "Ramcharitmanas (Tulsidas)",
        contextTag = "श्रद्धा"
    ),
    Suvichar(
        id = "suvichar_5",
        quote = "उठो, जागो और तब तक मत रुको जब तक कि लक्ष्य की प्राप्ति न हो जाए। स्वयं पर विश्वास ही ईश्वर पर विश्वास है।",
        quoteEn = "Arise, awake, and stop not until the goal is reached. Believing in yourself is believing in God.",
        authorOrSource = "स्वामी विवेकानंद",
        authorOrSourceEn = "Swami Vivekananda",
        contextTag = "आत्मबल"
    ),
    Suvichar(
        id = "suvichar_6",
        quote = "प्रार्थना केवल मांगना नहीं है, बल्कि यह अंतरात्मा की परमात्मा के साथ एक पावन बातचीत है। मौन में भी भगवान सुनते हैं।",
        quoteEn = "Prayer is not merely asking, but a sacred conversation of the soul with the Supreme. Even in silence, God listens.",
        authorOrSource = "अध्यात्म दर्शन",
        authorOrSourceEn = "Spiritual Darshan",
        contextTag = "प्रार्थना"
    )
)
