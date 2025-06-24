from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options
import time
import uuid

options = Options()
options.add_argument('--headless')
options.add_argument('--no-sandbox')
options.add_argument('--disable-dev-shm-usage')

driver = webdriver.Chrome(options=options)

login = str(uuid.uuid4())

try:
    driver.get("http://localhost:3000")
    time.sleep(1)

    # Click "Zarejestruj się!"
    driver.find_element(By.XPATH, '//button[normalize-space()="Zarejestruj się"]').click()

    # Fill registration form
    text_inputs = driver.find_elements(By.CSS_SELECTOR, 'input[type="text"]')
    password_inputs = driver.find_elements(By.CSS_SELECTOR, 'input[type="password"]')

    text_inputs[0].send_keys(login)
    password_inputs[0].send_keys("pass123")

    # Submit registration
    driver.find_element(By.XPATH, '//button[text()="Zarejestruj się"]').click()

    # Wait for login form
    time.sleep(1)

    # Fill login form
    text_inputs = driver.find_elements(By.CSS_SELECTOR, 'input[type="text"]')
    password_inputs = driver.find_elements(By.CSS_SELECTOR, 'input[type="password"]')

    text_inputs[0].send_keys("doesntexist")
    password_inputs[0].send_keys("pass123")

    driver.find_element(By.XPATH, '//button[text()="Zaloguj"]').click()

    time.sleep(1)

    body_text = driver.find_element(By.TAG_NAME, "body").text
    assert ("Nieprawidłowy login lub hasło") in body_text

finally:
    driver.quit()
