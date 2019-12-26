# Winter

Simple ORM library written in Java using Spring

## Project workflow

	1. Create fix or feature issue.
	2. Create new branch with name matching pattern fix/#{issue num.} or feature/#{issue num.}
	3. Work. Remember to pull new changes frequently.
	4. Commit.
	5. Rebase on top of master.
	6. Push. Solve conflicts.
	7. Create merge request.
	8. Fix bugs and commit.
	9. Merge to master.
	10. Pull from master and chack if everything works as expected and tests are passing. 

## Technologies

* Java 13 
* SpringBoot
* Postgre SQL

## Postgresql instalation
    sudo apt install postgresql
    sudo -iu postgres
    createdb winter
    createuser -P winter // password: admin
    
## Deploy
    In Intelliji IDEA:
        1. Maven clean install with tests enabled.
        2. Run WinterApplication, it's at localhost:8080.
        3. Check results in database.
