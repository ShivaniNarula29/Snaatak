In this Assignment  we will cover some basic command of linux

- First check your current working directory
        pwd

- Now create directory with name "linux" in your current directory.
        mkdir /linux

- Then create a another directory  with name "Assignment-01" inside your "linux" directory.
        -> cd linux
  linux -> mkdir /Assignment-01


- Now create one more directory inside "/tmp" with name "dir1" without changing your present direct>
 	 linux -> mkdir -p /tmp/dir1

**Running Inputs and outputs:**

![Screenshot from 2025-01-02 13-27-37](https://github.com/user-attachments/assets/a516130f-d9fa-44ed-aad0-1ff1d9324a9a)

![Screenshot from 2025-01-02 13-28-53](https://github.com/user-attachments/assets/ee9748e3-f296-4b99-b307-96ce3830cd95)

-----------------------------------------********************-----------------------------------

- At last create two more directories having below tree structure .It should create a below structure via single command only .
/tmp
  - dir1
     - dir2
     - dir3 
	  linux -> mkdir -p /tmp/dir1/dir2 /tmp/dir1/dir3
        
- Find a command  that will delete a "dir3" that you have created before.
   	  linux -> rmdir /tmp/dir1/dir3
			OR
	    	   cd /tmp/dir1
   	  dir1 ->  rmdir dir3

**Running Inputs and outputs:**

![Screenshot from 2025-01-02 13-33-01](https://github.com/user-attachments/assets/c4c6b4f1-46b4-4986-9f7a-90789046db78)
output:
![Screenshot from 2025-01-02 13-33-49](https://github.com/user-attachments/assets/38acfb4b-35d3-4236-a45f-039a0ece2ee2)

-----------------------------------------********************-----------------------------------
	
- Now create a empty file with your "first-name"  in /tmp directory.
	touch /tmp/first_name.txt
	chmod +x /tmp/first_name.txt

**Running Inputs and outputs:**

![Screenshot from 2025-01-02 13-37-16](https://github.com/user-attachments/assets/fe3b2ac2-10eb-4dbd-9237-a0bc96a67e4a)
![Screenshot from 2025-01-02 13-37-45](https://github.com/user-attachments/assets/7762dd38-1cf3-4a5d-96d2-c1f4d1c12b16)

-----------------------------------------********************-----------------------------------

- After creating a empty file , add "This is my first line " into a file without using any editor.
	echo "This is my first line" > /tmp/first_name.txt

- Now add  one more line "this is a additional content " into a same file .Make sure it will not overwrite the previous line of the file.
	echo -e "Line1\nLIne2\nLin3...\Line10" >>  first_name.txt

**Running Inputs and outputs:**

![Screenshot from 2025-01-02 13-43-27](https://github.com/user-attachments/assets/64567529-90e1-4efb-8fe0-8c2f81d07a7b)

![Screenshot from 2025-01-02 13-44-09](https://github.com/user-attachments/assets/5608a880-3900-42bd-99de-2d631f158d0c)


-----------------------------------------********************-----------------------------------

- Then  create new  file with your  "last-name" along with some content like "last-name is my last name".Do not use any editor
	echo "last name is my last name" >>  /tmp/last_name.txt

- Now add "this is line at the  beginning" into "last-name" file  in such a manner that it will add the line at beginning of the file.Do not use any editor.
	echo -e "content\n$(</tmp/last_name.txt)" > /tmp/last_name.txt

- Then add some more 8-10  lines to the same file .
	echo -e "Line1\nLIne2\nLin3\nLine4\nLine5\nLine6\nLine7\nLine8\nLine9\nLine10" >>  /tmp/last_name.txt

**Running Inputs and outputs:**

![Screenshot from 2025-01-02 13-46-56](https://github.com/user-attachments/assets/29bd7530-5bd1-46ff-8d39-a00a863e68e6)

![Screenshot from 2025-01-02 13-48-22](https://github.com/user-attachments/assets/230df18a-05eb-48d3-9a4c-f461c3c409b0)


-----------------------------------------********************-----------------------------------

- Now find a command that will show:
  - top 5 lines of the file.
	head -n 5 last_name.txt

  - bottom 2 lines of the  file.
	tail -n 2 /tmp/last_name.txt

  - only 6th line  of the file.
	head -n 6 /tmp/last_name.txt | tail -n 1

  - 3-8 lines of the file .
	 head -n 8 /tmp/last_name.txt | tail -n 6

**Running Inputs and outputs:**

![Screenshot from 2025-01-02 13-51-06](https://github.com/user-attachments/assets/91e30b5b-938c-416f-a049-6599d4bc7c84)

-----------------------------------------********************-----------------------------------
- Find a command that will list  below things of /tmp directory
  - list all content(including hidden files)
	ls -al /tmp
  - list only files
	ls -l /tmp | grep "^-"
  - list only directories
 	ls -l /tmp | grep "^d"

**Running Inputs and outputs:**

![Screenshot from 2025-01-02 13-52-37](https://github.com/user-attachments/assets/52efcf7d-7fed-4dc4-a1d7-6a537bee4e66)
![Screenshot from 2025-01-02 13-53-24](https://github.com/user-attachments/assets/9f883833-4498-4459-8114-b4aefaa3b1c2)

-----------------------------------------********************-----------------------------------
- Now copy the "last-name" into the /tmp/dir2 with same name.
	cp last_name.txt /tmp/dir1/dir2

- Then again copy the "last-name" into the /tmp/dir2, this time with different name i.e "last-name".copy
	cp last_name.txt /tmp/dir1/dir2/last_name1.txt

**Running Inputs and outputs:**

![Screenshot from 2025-01-02 14-31-16](https://github.com/user-attachments/assets/1e5d0c9a-4fbb-44cc-a1df-39df36c6acdd)

-----------------------------------------********************-----------------------------------


- Now change the name of the "first-name" file  to some other name at same location .
	mv  first_name.txt first_name_new.txt

- Find a command that will  move the "last-name" file  to /tmp/dir1
	mv  first_name_new.txt /tmp/dir1

**Running Inputs and outputs:**

![Screenshot from 2025-01-02 14-36-38](https://github.com/user-attachments/assets/fb237842-e9fd-433d-8157-7736317c7793)

-----------------------------------------********************-----------------------------------

- find a command that will clear the content of /tmp/dir2/"last-name".copy .Make sure it will not even contain  empty line .
	> last_name1.txt
- Now delete the same file i.e /tmp/dir2/last-name.copy
	rm -r last_name1.txt

**Running Inputs and outputs:**

![Screenshot from 2025-01-02 14-38-53](https://github.com/user-attachments/assets/b92344c7-4e7a-448b-9db3-1e8bb7a66e87)

![Screenshot from 2025-01-02 14-40-17](https://github.com/user-attachments/assets/bf2806ff-dff5-45d1-8952-6351524d6e57)


Note : Do not use sed command in this assignment.
