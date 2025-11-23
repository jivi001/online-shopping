import os
import urllib.request

print("Downloading MySQL connector...")

# Create lib directory if it doesn't exist
if not os.path.exists("lib"):
    os.makedirs("lib")

# Download MySQL JDBC connector
url = "https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar"
dest = "lib/mysql-connector-j-8.0.33.jar"

try:
    urllib.request.urlretrieve(url, dest)
    print(f"✓ Downloaded: {dest}")
except Exception as e:
    print(f"✗ Download failed: {e}")
    exit(1)
