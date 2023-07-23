package peaksoft.dao.impl;

import peaksoft.config.Config;
import peaksoft.dao.JobDao;
import peaksoft.model.Job;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JobDaoImpl implements JobDao {
    @Override
    public void createJobTable() {
        String sql = "CREATE TABLE IF NOT EXISTS jobs (" +
                "id SERIAL PRIMARY KEY," +
                "position VARCHAR," +
                "profession VARCHAR," +
                "description VARCHAR," +
                "experience INT)";

        try (
                Connection connection = Config.getConnection();
                Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(sql);
            System.out.println("table jobs created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addJob(Job job) {
        String sql = "INSERT INTO jobs (" +
                "position, profession, description, experience)" +
                "VALUES (?, ?, ?, ?)";
        try (
                Connection connection = Config.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, job.getPosition());
            preparedStatement.setString(2, job.getProfession());
            preparedStatement.setString(3, job.getDescription());
            preparedStatement.setInt(4, job.getExperience());
            preparedStatement.executeUpdate();
            System.out.println("job was added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Job getJobById(Long jobId) {
        String sql = "SELECT * FROM jobs WHERE id = ?";
        Job job = new Job();
        try (
                Connection connection = Config.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setLong(1, jobId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                job.setId(resultSet.getLong("id"));
                job.setPosition(resultSet.getString("position"));
                job.setProfession(resultSet.getString("profession"));
                job.setDescription(resultSet.getString("description"));
                job.setExperience(resultSet.getInt("experience"));
            } else {
                throw new RuntimeException("Job with id: " + jobId + "not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return job;
    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        String sql = "";
        if(ascOrDesc.equalsIgnoreCase("desc")) sql = "SELECT * FROM jobs ORDER BY experience desc";
        else if (ascOrDesc.equalsIgnoreCase("asc")) sql = "SELECT * FROM jobs ORDER BY experience";
        else throw new RuntimeException("problem in your mind and desc/asc");
        List<Job> jobList = new ArrayList<>();
        try (
                Connection connection = Config.getConnection();
                Statement statement = connection.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                jobList.add(new Job(
                                resultSet.getLong("id"),
                                resultSet.getString("position"),
                                resultSet.getString("profession"),
                                resultSet.getString("description"),
                                resultSet.getInt("experience")
                        )
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jobList;
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        Job job = new Job();
        String sql = "SELECT * FROM jobs JOIN employees e ON jobs.id = e.job_id WHERE e.id = ?";
        try (
                Connection connection = Config.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setLong(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                job.setId(resultSet.getLong("id"));
                job.setPosition(resultSet.getString("position"));
                job.setProfession(resultSet.getString("profession"));
                job.setDescription(resultSet.getString("description"));
                job.setExperience(resultSet.getInt("experience"));
            } else {
                throw new RuntimeException("job with employee id: " + employeeId + "not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return job;
    }

    @Override
    public void deleteDescriptionColumn() {
        String sql = "UPDATE jobs SET description = NULL WHERE id = ?";
        try (
                Connection connection = Config.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
