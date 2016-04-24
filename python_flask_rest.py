import flask
from flask import Flask
from flask import request
from flask import render_template
from flask import *
import urllib2
import json
import os
import httplib, urllib, base64, json
import subprocess
import StringIO
#import PIL
#from PIL import Image

app = Flask(__name__)


#Subscribe for a valid api key to use any  HPE API's
API_KEY='5c6ae2aa-6e4a-4015-a616-ae17aa36f49e'
#API_KEY='bd6f2ed3-4cbb-42a2-9bce-02c5634ea349'

@app.route("/sentiment")
def fetchSentiment():
	#"https://api.havenondemand.com/1/api/sync/analyzesentiment/v1?text=I+am+a+good+boy&apikey=5c6ae2aa-6e4a-4015-a616-ae17aa36f49e"
	url=""
	url+="https://api.havenondemand.com/1/api/sync/analyzesentiment/v1?text="
	queryString= request.args.get('current')
	queryString=queryString.replace(" ","+")
	print queryString
	url+=queryString
	url+="&apikey="
	url+=API_KEY
	print url
	result=urllib2.urlopen(url).read()
	
	jsonData=json.loads(result)
	print jsonData
	sentiment=jsonData["aggregate"]["sentiment"]
	score=jsonData["aggregate"]["score"]
	retVal={}
	retVal["1"]=sentiment
	retVal["2"]=score
	return json.dumps(retVal)
	
@app.route("/autocomplete")
def autoComplete():
	#"https://api.havenondemand.com/1/api/sync/autocomplete/v1?text=sch&apikey=5c6ae2aa-6e4a-4015-a616-ae17aa36f49e"
	url=""
	url+="https://api.havenondemand.com/1/api/sync/autocomplete/v1?text="
	queryString= request.args.get('current')
	queryString=queryString.replace(" ","+")
	print queryString
	url+=queryString
	url+="&apikey="
	url+=API_KEY
	print url
	result=urllib2.urlopen(url).read()
	
	jsonData=json.loads(result)
	sentiment=jsonData["aggregate"]["sentiment"]
	retVal={}
	retVal["1"]=sentiment
	return json.dumps(retVal)

@app.route("/tokenize")
def tokenize():
	
	#"https://api.havenondemand.com/1/api/sync/tokenizetext/v1?text=probability+theory&apikey=5c6ae2aa-6e4a-4015-a616-ae17aa36f49e"
	
	try:
		url=""
		url+="https://api.havenondemand.com/1/api/sync/tokenizetext/v1?text="
		queryString= request.args.get('current')
		queryString=queryString.replace(" ","+")
		print queryString
		url+=queryString
		url+="&apikey="
		url+=API_KEY
		print url
		result=urllib2.urlopen(url).read()
		
		jsonData=json.loads(result)
		print jsonData
		#sentiment=jsonData["aggregate"]["sentiment"]
		#retVal={}
		#retVal["1"]=sentiment
		return json.dumps(jsonData)
	except Exception as e:
		print e
@app.route("/facedimension")
def faceDetect():
	try:
		binaryImage= request.args.get('current')
		binaryImage=binaryImage.strip("\n")
		binaryImage=binaryImage.replace(" ","+")
		print binaryImage

		url=""
		url+="https://api.havenondemand.com/1/api/sync/detectfaces/v1?url="
		fh = open("emotion.jpg", "wb")
		fh.write(binaryImage.decode('base64'))
		fh.close()
		url+="http://52.160.99.213:8000/emotion.jpg"
		url+="&additional=false&apikey="
		url+=API_KEY
		print url
		results=urllib2.urlopen(url).read()
		jsonData=json.loads(results)
		print jsonData
		#topWord=jsonData["words"][0]
		return json.dumps(jsonData)
	except Exception as e:
		return "Exception Occured"+e



@app.route("/faceemotionDetection")
def faceemotion():
	try:
		print "Entered Function"
		binaryImage= request.args.get('current')
		binaryImage=binaryImage.strip("\n")
		binaryImage=binaryImage.replace(" ","+")
		print "Binary Image"
		print binaryImage
		print "printed Binary Image"
		fh = open("emotion.jpg", "wb")
		fh.write(binaryImage.decode('base64'))
		fh.close()
		'''
		im1=Image.open('emotion.jpg')
		buffer=StringIO.String()
		im1.save(buffer,"JPEG",quality=10)

		with open("./emotionC.jpg","w") as handle:
			handle.write(buffer.contents())
		'''
		headers = {
	    'Content-Type': 'application/json',
	    'Ocp-Apim-Subscription-Key': 'a1f1329e4ae449d4b0c4ec80c0e9c7e1',
		}

		params = urllib.urlencode({
		})
		conn = httplib.HTTPSConnection('api.projectoxford.ai')
		conn.request("POST", "/emotion/v1.0/recognize?%s" % params, '{ "url": "http://52.160.99.213:8000/emotion.jpg" }', headers)
		response = conn.getresponse()
		data = response.read()
		parsed_data = json.loads(data)
		emotion_range = []
		for i in ['sadness','neutral','contempt','disgust','anger','surprise','fear','happiness']:
			emotion_range.append((i,parsed_data[0]['scores'][i]))
		color_map = dict({
	        'sadness':'00059A',
	        'neutral':'000000',
	        'contempt':'A00130',
	        'disgust':'00220B',
	        'anger':'B00100',
	        'surprise':'FF9A00',
	        'fear':'B26030',
	        'happiness':'FFF400'
	        })
		def cmp_items(a, b):
			if a[1] > b[1]:
				return -1
			elif a[1] == b[1]:
				return 0
			else:
				return 1
		emotion_range.sort(cmp_items)
		result=color_map[emotion_range[0][0]],emotion_range[0][0]
		retVal={}

		retVal[result[0]]=result[1]
		return json.dumps(retVal)
		print result
		conn.close()
		return retVal

	except Exception as e:
		return "Exception Occured"+e

@app.route("/neuralAmrit")
def neuralPrediction():
	sentence= request.args.get('current')
	sentence = sentence.replace(" ", "_")
	print sentence
	out = ""
	for i in range(3):
		out = subprocess.check_output(["sudo","python","/usr/local/lib/python2.7/dist-packages/tensorflow/models/rnn/ptb/ptb_word_lm_logits.py","--data_path=/usr/local/lib/python2.7/dist-packages/tensorflow/models/rnn/ptb/tmp/simple-examples/data/","--model","small","--work","predict","--string", sentence])

		sentence = sentence + "_" + out
	co=1
	words=sentence.split('_')
	print words
	retVal={}
	for word in words:
		retVal[str(co)]=word
		co+=1
	print retVal
	return json.dumps(retVal)

@app.route("/query")
def query():
	data = request.args.get('current')
	return json.loads("Successfully received: "+ data)

@app.route("/")
def sayHello():
	s='{"1":"Hello World"}'
	return s

if __name__ == "__main__":
    	app.run(host='0.0.0.0')
