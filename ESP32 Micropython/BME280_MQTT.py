from machine import Pin, SoftI2C, deepsleep
from umqtt.simple import MQTTClient
import network
import neopixel
from utime import sleep, time
import utime
import ntptime
import BME280
from math import pow

# Verbinde dich mit dem WLAN-Netzwerk
wifi_ssid = 'WLANNAME'
wifi_password = 'WLANPW'


# MQTT-Konfiguration
mqtt_server = "mqtt-dashboard.com"
mqtt_topic_temp = "WeatherWear/Temp"
mqtt_topic_airpressure = "WeatherWear/AirPressure"
mqtt_topic_humidity = "WeatherWear/Humidity"
mqtt_topic_timestamp = "WeatherWear/Timestamp"
mqtt_message = "12"

# ESP32 - Pin assignment
BME280Power = Pin(0, Pin.OUT)
BME280Power.value(1)
sleep(3)
NUM_LEDS = 1
# Initialisiere die NeoPixel-LED an GPIO 10
pin = 10
np = neopixel.NeoPixel(Pin(pin), NUM_LEDS)



# Funktion zum Setzen der Farbe der RGB-LED
def set_color(r, g, b):
    np[0] = (r, g, b)
    np.write()



def calculate_sea_level_pressure(P, T, h):
    """
    Berechnet den auf Meereshöhe reduzierten Luftdruck nach einer vereinfachten Formel,
    die die Temperatur berücksichtigt.

    :param P: Gemessener Luftdruck in hPa
    :param T: Temperatur in °C
    :param h: Höhe über dem Meeresspiegel in Metern
    :return: Auf Meereshöhe reduzierter Luftdruck in hPa
    """
    # Temperatur in Kelvin
    T_kelvin = T + 273.15

    # Berechne den Faktor (1 - h / (44330.0 * (1 + T / 273.15)))
    factor = 1 - (h / (44330.0 * (1 + (T / 273.15))))

    # Berechne den Exponenten
    exponent = -5.255

    # Erhebe den Faktor zur Potenz des Exponenten
    adjusted_factor = pow(factor, exponent)

    # Berechnung des auf Meereshöhe reduzierten Drucks
    P0 = P * adjusted_factor

    return P0



# WiFi-Verbindung herstellen
def connect_wifi():
    sta_if = network.WLAN(network.STA_IF)
    if not sta_if.isconnected():
        print('Verbinde mit WiFi...')
        sta_if.active(True)
        sta_if.connect(wifi_ssid, wifi_password)
        while not sta_if.isconnected():
            pass
    print('Verbunden mit WiFi:', sta_if.ifconfig())
    
# Funktion zum Abrufen der Zeit von einem NTP-Server
def get_time_from_ntp():
    print("Zeit von NTP-Server abrufen...")
    ntptime.settime()  # Setze die Zeit basierend auf der NTP-Server-Antwort
    print("Aktuelle Zeit:", utime.localtime())

# MQTT-Client initialisieren und verbinden
def connect_mqtt():
    global client
    client = MQTTClient("esp32", mqtt_server,keepalive=30)
    client.connect()




# Hauptprogramm
def main():
    try:
        i2c = SoftI2C(scl=Pin(1), sda=Pin(2), freq=10000)
        sleep(0.1)
        bme = BME280.BME280(i2c=i2c)
        sleep(0.1)  
        set_color(255,0,0)           
        # Nachricht veröffentlichen
        temp = bme.temperature
        sleep(0.1)
        hum = bme.humidity
        sleep(0.1)
        pres = bme.pressure
        print('Pressure1: ', pres)
        pres = float(bme.pressure[:-3]) #hPa entfernen
        pres= calculate_sea_level_pressure(pres,float(temp[:-1]),175)
        pres = str(pres)+'hpa'
        sleep(0.1)
        print('Temperature: ', temp)
        print('Humidity: ', hum)
        print('Pressure: ', pres)
        set_color(0,255,0)
        
        connect_wifi()
        get_time_from_ntp()
        connect_mqtt()
      
        # Aktuelle Zeitstempel als Sekunden seit dem 1. Januar 2000 UTC
        current_time = utime.time()
        # Konvertiere die Zeitstempel in eine Struktur
        time_struct = utime.localtime(current_time)
        # Zeit und Datum als String im Format "Jahr-Monat-Tag Stunde:Minute:Sekunde"
        current_datetime_str = "{:04d}-{:02d}-{:02d} {:02d}:{:02d}:{:02d}".format(
            time_struct[0], time_struct[1], time_struct[2],
            time_struct[3], time_struct[4], time_struct[5]
        )

        print('Time: ', current_datetime_str)
        
        
        client.publish(mqtt_topic_temp, temp, retain=True, qos=1)
        client.publish(mqtt_topic_airpressure, pres, retain=True, qos=1)
        client.publish(mqtt_topic_humidity, hum, retain=True, qos=1)
        client.publish(mqtt_topic_timestamp, current_datetime_str, retain=True, qos=1)
#            BME280Power.value(0)
        set_color(0,0,0)
        deepsleep(900000)
    except Exception as e:
         # Wenn eine Ausnahme auftritt, führe diesen Block aus
        print("Ein Fehler ist aufgetreten:", e)        
        deepsleep(10000) #5 Sekunden
       
 

if __name__ == "__main__":
    main()