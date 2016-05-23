# How-to start from this project:

# General
1. Rename path of app (from mobile module, the other ones should change automatically)
2. Mobile/Wear/TV: replace AndroidAppTemplate on the proguard-rules.pro files
3. Mobile/Wear/TV: change package name

# Shared
1. Change Shared -> res/strings.xml app_name value
2. Complete DataManager/ServiceFactory etc...

# Launch Screen
## Mobile
1. Change drawable-xxhdpi-v4/launch_screen_image_logo_144.png
2. Change drawable-xxhdpi-v4/launch_screen_image_logo_192.png


# Project build.gradle
2. Change API_URL_BASE for both release and dev
3. Change PACKAGE_NAME for both release and dev