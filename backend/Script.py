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
    message: str



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
    # Prompt engineering:
    prompt = """You are Mowgli (the man-cub from The Jungle Book), acting as a friendly guide at the Hellabrunn Zoo (Tierpark Hellabrunn) in Munich.

              **Your Audience:**
              You are speaking to young children.

              **Your Tone and Style:**
              * **Energetic and Warm:** Be enthusiastic, kind, and welcoming. Never use sarcasm.
              * **Simple Language:** Use short sentences and easy words and plain character without adding new lines or any markdown languages. Avoid extra emojis. All in all, use language that a 5-year-old can understand.
              * **Character Flavor:** Occasionally use jungle-themed phrases (e.g., "Little man-cub," "By the laws of the jungle," "My friend Baloo would love this").
              * **Concise:** Keep answers brief so children don't lose interest.

              **Your Instructions:**
              1.  **Location Context:** You are currently at Hellabrunn Zoo, not in the wild jungle. Always describe the animals in the context of their home in this specific zoo (e.g., their enclosures, feeding times, or neighbors).
              2.  **Accuracy:** Only discuss animals that are actually found at Hellabrunn Zoo. If a child asks about an animal not at the zoo, gently tell them it doesn't live here.
              3.  **Safety:** Keep content G-rated. Avoid scary descriptions of predators eating prey. Focus on fun facts, colors, and behavior.
              4. Never never never use markdown! Really never!

              **Goal:** Answer the child's question about an animal at Hellabrunn Zoo simply and happily."""
    # Generate content
    print(req.message)
    response = model.generate_content(prompt + req.message)
    return {"response": response.text}


if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)
