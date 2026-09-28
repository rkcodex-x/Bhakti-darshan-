package com.example.data.model

data class PujaStep(
    val stepNumber: Int,
    val title: String,
    val titleEn: String,
    val subtitle: String,
    val subtitleEn: String,
    val instruction: String,
    val instructionEn: String,
    val mantraOrText: String,
    val iconEmoji: String,
    val defaultTargetCount: Int = 1 // for jaap or audio iterations
)

val GuidedPujaSteps = listOf(
    PujaStep(
        stepNumber = 1,
        title = "भगवान के दर्शन एवं ध्यान",
        titleEn = "Divine Darshan & Dhyana",
        subtitle = "मन को शांत कर इष्टदेव का स्मरण करें",
        subtitleEn = "Calm your mind and meditate on the Divine",
        instruction = "अपने हाथ जोड़ें, तीन गहरी श्वास लें और भगवान के दिव्य रूप का अंतर्मन में ध्यान करें।",
        instructionEn = "Fold your hands, take three deep breaths and meditate on the divine presence in your heart.",
        mantraOrText = "शान्ताकारं भुजगशयनं पद्मनाभं सुरेशं। विश्वाधारं गगनसदृशं मेघवर्णं शुभाङ्गम्॥",
        iconEmoji = "🛕"
    ),
    PujaStep(
        stepNumber = 2,
        title = "पवित्र मंत्र जाप",
        titleEn = "Sacred Mantra Jaap",
        subtitle = "11 अथवा 21 बार पावन ॐ व गायत्री मंत्र जाप",
        subtitleEn = "Chant Om & Gayatri Mantra 11 or 21 times",
        instruction = "नीचे दिए गए 'ॐ' जप बटन को दबाकर प्रत्येक जाप पूर्ण करें। कंपन और ध्वनि से सकारात्मक ऊर्जा का संचार होगा।",
        instructionEn = "Tap the 'Om' bead counter button to record each chant with gentle haptic vibration feedback.",
        mantraOrText = "ॐ भूर्भुवः स्वः तत्सवितुर्वरेण्यं भर्गो देवस्य धीमहि धियो यो नः प्रचोदयात्॥",
        iconEmoji = "📿",
        defaultTargetCount = 11
    ),
    PujaStep(
        stepNumber = 3,
        title = "सुबह का भजन श्रवण",
        titleEn = "Morning Bhajan Listening",
        subtitle = "मधुर भक्ति रस में लीन हों",
        subtitleEn = "Immerse in peaceful morning hymns",
        instruction = "शांत भाव से प्रातःकालीन भजन सुनें। मन के समस्त द्वेष और तनाव को भगवान के चरणों में समर्पित कर दें।",
        instructionEn = "Listen with a pure heart. Surrender all doubts and worries to the Divine.",
        mantraOrText = "रघुपति राघव राजाराम, पतित पावन सीताराम। ईश्वर अल्ला तेरो नाम, सब को सन्मति दे भगवान॥",
        iconEmoji = "🎵"
    ),
    PujaStep(
        stepNumber = 4,
        title = "पावन दीप एवं आरती",
        titleEn = "Sacred Aarti & Diya",
        subtitle = "थाली घुमाकर भगवान की आरती करें",
        subtitleEn = "Wave the sacred Aarti thali",
        instruction = "स्क्रीन पर आरती की थाली को स्पर्श कर घुमाएं। मंदिर की पवित्र घंटी और शंखनाद के साथ आरती गाएं।",
        instructionEn = "Touch and circulate the glowing Aarti thali while temple bells and shankh resonate.",
        mantraOrText = "कर्पूरगौरं करुणावतारं संसारसारम् भुजगेन्द्रहारम्। सदावसन्तं हृदयारविन्दे भवं भवानीसहितं नमामि॥",
        iconEmoji = "🪔"
    ),
    PujaStep(
        stepNumber = 5,
        title = "प्रार्थना एवं संकल्प",
        titleEn = "Morning Prayer & Sankalpa",
        subtitle = "आज के दिन के लिए मंगल कामना",
        subtitleEn = "Auspicious resolve for the day",
        instruction = "हे प्रभु! आज का दिन सद्भाव, सेवा और सत्य के मार्ग पर बीते। सभी प्राणियों का कल्याण हो।",
        instructionEn = "May this day be guided by kindness, wisdom, and peace for all beings.",
        mantraOrText = "सर्वे भवन्तु सुखिनः सर्वे सन्तु निरामयाः। सर्वे भद्राणि पश्यन्तु मा कश्चिद्दुःखभाग्भवेत्॥",
        iconEmoji = "🙏"
    )
)
