import requests
from bs4 import BeautifulSoup as bs
import urllib.request
import urllib.parse

url = 'https://www.musinsa.com/app/'
values = {'name': 'nesffer',
          'query': 'python'}
headers = {'User-Agent': 'Mozilla/5.0'}
data = urllib.parse.urlencode(values).encode('utf-8')
req = urllib.request.Request(url, data, headers)
f = urllib.request.urlopen(req)
musinsa = bs(f, 'html.parser')

print(musinsa)