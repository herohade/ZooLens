import google.generativeai as genai
import os
from pydantic import BaseModel
from fastapi import FastAPI, HTTPException
import uvicorn
import json

# Create FastAPI app
app = FastAPI()
# Configure the API key
genai.configure(api_key=os.environ.get("GEMINI_API_KEY"))

# Create the model
model = genai.GenerativeModel('gemini-2.5-flash')


class PromptRequest(BaseModel):
    prompt: str



# Health check
@app.get("/animals")
def animals():
    with open("resources/data/animals.json", "r") as f:
        animals = json.load(f)
    return animals



# Health check
@app.get("/")
async def root():
    return {"message": "API is running"}



# Health check
@app.post("/chat")
def chat(req: PromptRequest):
    # Generate content
    print(req.prompt)
    response = model.generate_content(req.prompt)
    return {"response": response.text}


if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)
