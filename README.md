# Quiz
App that allows the user to set up a quiz, and answer the questions

## Installation
### Install Docker
- On Windows and Mac
```
Install Docker Desktop
Windows: https://docs.docker.com/desktop/install/windows-install/
Mac: https://docs.docker.com/desktop/install/mac-install/
```
- On Linux
```bash
# For Debian-based systems
sudo apt install -y docker.io
sudo apt install docker-compose

#For Arch-based systems
sudo pacman -S docker.io
sudo pacman -S docker-compose

# Making Docker run now and on startup
sudo systemctl start docker
sudo systemctl enable docker

# Using the CLI without sudo 
sudo usermod -aG docker $USER
sudo reboot
```

## How to run
- Navigate to the root of the project
- Open a terminal
- Start the containers: 
```bash
docker compose up
```
- Visit localhost:4200 to play the quiz!


## Benchmarking
- Go to the /scripts folder and run the LoadTestMultiple.sh script
- Inside you can change how many sessions should be created (set to 6 by default)
- In the /scripts/benchmarks folder you can find some benchmarks I've done

## Notes
- If the data is not present, it will try to query it from the external API, which takes significantly longer (only 1 request per 5 seconds)
- When running it locally, give the backend a few minutes to gather data in bulk (should be completely done in ~7 minutes)
- It refreshes this data every night in the background, so it stays up to date