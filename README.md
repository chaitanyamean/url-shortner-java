<img width="694" height="358" alt="image" src="https://github.com/user-attachments/assets/b220b1c0-a1bf-43b4-897e-34ea9030c3d5" />SQL lite connection:
in application properties add 
spring.datasource.url=jdbc:sqlite:data/shortener.db

For /shorten creation u can view
src/test/java/com/url_shortner/project/UrlShortnerProjectApplicationTests.java

## Performance Benchmarks
We conducted a load test using **k6** with 10 concurrent users.

**Results:**


<img width="385" height="200" alt="Screenshot 2025-12-30 at 3 56 59 PM" src="https://github.com/user-attachments/assets/1d4181a8-3ea0-40bb-9e69-ee815f15379f" />


https://docs.google.com/document/d/1cpHX5ujZBnG246eEZJVFnAtG_G7JbSYxFTOeS04JaiY/edit?usp=sharing
