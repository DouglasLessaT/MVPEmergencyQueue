# Google Maps Setup Guide

This Flutter app uses Google Maps for displaying hospital locations. Follow these steps to configure the Google Maps API:

## 1. Get a Google Maps API Key

1. Go to the [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select an existing one
3. Enable the following APIs:
   - Maps SDK for Android
   - Maps SDK for iOS
   - Maps JavaScript API (for web)
4. Create credentials (API Key)
5. Restrict the API key to your app's bundle ID/package name

## 2. Configure Android

1. Open `android/app/src/main/AndroidManifest.xml`
2. Replace `YOUR_GOOGLE_MAPS_API_KEY` with your actual API key:
   ```xml
   <meta-data
       android:name="com.google.android.geo.API_KEY"
       android:value="YOUR_ACTUAL_API_KEY_HERE" />
   ```

## 3. Configure iOS

1. Open `ios/Runner/AppDelegate.swift`
2. Add the following import at the top:
   ```swift
   import GoogleMaps
   ```
3. Add the API key in the `application` method:
   ```swift
   GMSServices.provideAPIKey("YOUR_ACTUAL_API_KEY_HERE")
   ```

## 4. Configure Web (Optional)

1. Open `web/index.html`
2. Replace `YOUR_API_KEY` in the Google Maps script tag:
   ```html
   <script>
     (g=>{var h,a,k,p="The Google Maps JavaScript API",c="google",l="importLibrary",q="__ib__",m=document,b=window;b=b[c]||(b[c]={});var d=b.maps||(b.maps={}),r=new Set,e=new URLSearchParams,u=()=>h||(h=new Promise(async(f,n)=>{await (a=m.createElement("script"));e.set("libraries",[...r]+"");for(k in g)e.set(k.replace(/[A-Z]/g,t=>"_"+t[0].toLowerCase()),g[k]);e.set("callback",c+".maps."+q);a.src=`https://maps.${c}apis.com/maps/api/js?`+e;d[q]=f;a.onerror=()=>h=n(Error(p+" could not load."));a.nonce=m.querySelector("script[nonce]")?.nonce||"";m.head.append(a)}));d[l]?console.warn(p+" only loads once. Ignoring:",g):d[l]=(f,...n)=>r.add(f)&&u().then(()=>d[l](f,...n))})({
       key: "YOUR_ACTUAL_API_KEY_HERE",
       v: "weekly",
     });
   </script>
   ```

## 5. Test the App

After configuring the API keys, run the app:

```bash
flutter run
```

The map should now display properly with hospital markers.

## Security Notes

- Never commit your API keys to version control
- Use environment variables or secure key management in production
- Restrict your API keys to specific bundle IDs and IP addresses
- Monitor your API usage in the Google Cloud Console

## Troubleshooting

If the map doesn't load:
1. Check that your API key is correct
2. Verify that the required APIs are enabled
3. Check that your API key restrictions allow your app
4. Look for error messages in the console 