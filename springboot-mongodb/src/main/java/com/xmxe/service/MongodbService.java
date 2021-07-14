package com.xmxe.service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.xmxe.dao.MongodbDao;
import com.xmxe.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongodbService {

	private MongoTemplate mongoTemplate;

	private MongodbDao mongodbDao;

	@Autowired
	public void setMongoTemplate(MongoTemplate mongoTemplate){
		this.mongoTemplate = mongoTemplate;
	}

	@Autowired
	public void setMongodbDao(MongodbDao mongodbDao){
		this.mongodbDao = mongodbDao;
	}

	//查询所有
	public List<Student> findAllStudent() {
		return mongodbDao.findAll();
	}

	//添加
	public void insert(Student student) {
		mongodbDao.insert(student);
	}

	//删除
	public void delete(Integer id) {
		mongodbDao.deleteById(id);
	}

	//修改
	public void update(Student student) {
		mongodbDao.save(student);
	}

	public void doMongodbTemplate(){
		int id = 1,curPage = 2,size = 10;
		Student Student = new Student();
		mongoTemplate.insert(Student);//插入数据，会自动插入到对应的集合中
		List<Student> list = mongoTemplate.findAll(Student.class);//查询集合的所有数据
		Student student = mongoTemplate.findById(id,Student.class);//根据id查询

		Query query = new Query(Criteria.where("name").is("wang").and("age").is("20"));//Query是条件构造器，这个条件代表name是wang,age是20
		mongoTemplate.find(query,Student.class);//条件查询
		mongoTemplate.find(query.skip((curPage-1)*size).limit(size),Student.class);//分页查询，query.skip((curPage-1)*size).limit(size)表示跳过（当前页-1）*每页的大小条数据，输出size条数据

		Update update = new Update();
		update.set("name","li");
		UpdateResult upsert = mongoTemplate.upsert(query,update,Student.class);//修改，跟别的不同的是修改需要把修改的内容传递给一个Update对象
		Long count1 = upsert.getModifiedCount();  //得到受影响的数据数

		DeleteResult remove = mongoTemplate.remove(query,Student.class);//删除
		Long count2 = remove.getDeletedCount();//得到受影响的数据数
	}

}
