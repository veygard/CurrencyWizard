# CurrencyWizzard!

![app_icon](https://user-images.githubusercontent.com/79571688/188383806-5ec23baa-12fa-4e99-a974-c5553be08986.png)

Android app for currency exchange information: 
- You can select the currency and obtain the actual exchange rate.  
- You can add currencies to favorites
- You can enter the amount and convert it between currencies.

Clean architecture. MVVM. UI made by Jetpack Compose.
Stack: Kotlin, hilt, retrofit+okhttp, jetpack compose, room database, sqlite, lottie animation.

Important note: the api key expires after 7 days of trial. There may be a need to create accaunt on https://www.fastforex.io 
and set new Api_key in Constants(package com.veygard.currencywizard.util)


Features:

Search bar with autocomplite:

![auto](https://user-images.githubusercontent.com/79571688/188390888-9b5cf742-c587-4f98-a23b-5c92631b0d09.gif)

Add/Remove to favorites(from main list / from autocomplete list): 

![favorites](https://user-images.githubusercontent.com/79571688/188390931-0e71c185-1d52-42fa-b3bb-a7ea98e1cb80.gif)

Converting currencies:

![convert](https://user-images.githubusercontent.com/79571688/188391023-cc6a42a5-6284-4926-a749-e64628f14499.gif)

Sorting lists:

![sorting](https://user-images.githubusercontent.com/79571688/188391274-5f2ba3f9-36b7-4117-8408-579fa5c2ecbb.gif)

Connection error scenario:

![error](https://user-images.githubusercontent.com/79571688/188391831-ac4c90d6-3702-4863-87e0-95d8618d84eb.gif)

Starting page with lottie animation:

![start](https://user-images.githubusercontent.com/79571688/188391295-ac28115a-b0ca-4f9e-8764-4f8764eb8829.gif)
