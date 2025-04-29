#include <iostream>
#include <fstream>
#include <string>
#include "json.hpp"

using namespace std;
using json = nlohmann::json;

// Your real API Key
string API_KEY = "39b8e01beae02d172351b502434b830a";

int main() {
    string city;
    cout << "Enter your city: ";
    getline(cin, city);

    // Prepare the curl command
    string command = "curl -s \"http://api.openweathermap.org/data/2.5/weather?q=" 
                    + city + "&appid=" + API_KEY + "&units=metric\" > weather.json";

    // Execute the curl command (downloads JSON to a file)
    system(command.c_str());

    // Open the downloaded JSON file
    ifstream file("weather.json");
    if (!file.is_open()) {
        cout << " Failed to fetch weather data.\n";
        return 1;
    }

    json weatherData;
    file >> weatherData;
    file.close();

    if (weatherData.contains("main")) {
        double temp = weatherData["main"]["temp"];
        int humidity = weatherData["main"]["humidity"];
        double windSpeed = weatherData["wind"]["speed"];
        string condition = weatherData["weather"][0]["description"];

        cout << "\n Weather Report for " << city << ":\n";
        cout << "--------------------------------------\n";
        cout << "Condition  : " << condition << endl;
        cout << "Temperature: " << temp << "°C\n";
        cout << "Humidity   : " << humidity << "%\n";
        cout << "Wind Speed : " << windSpeed << " m/s\n";
        cout << "--------------------------------------\n";

        if (temp > 30)
            cout << "  Warning: It's very hot!\n";
        else if (temp < 10)
            cout << "  Cold weather alert!\n";
        else
            cout << " Pleasant weather!\n";
    }
    else {
        cout << " City not found. Please check spelling.\n";
    }

    // Optionally delete the JSON file after
    remove("weather.json");

    return 0;
}
