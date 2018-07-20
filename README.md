# KotlinStorm
abandon old and tedious Java, make android program to be simlple!

###startActvitity simplity
```
//in Java
btnTest.setOnClickListener(view->{
          startActivity(new Intent(this, SecondActivity.class)
                  .putExtra("name","lilei")
                  .putExtra("age",12)
                  .putExtra("isMale",false));
      });

//in kotlin
btnTest.click {
  go(SecondActivity::class.java,"name" to "lelei", "age" to 12, "isMale" to false)
}
```
