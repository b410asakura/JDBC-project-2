package peaksoft.dao.impl;

import peaksoft.config.Config;
import peaksoft.dao.EmployeeDao;
import peaksoft.model.Employee;
import peaksoft.model.Job;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDao {

    @Override
    public void createEmployeeTable() {
        String sql = "CREATE TABLE IF NOT EXISTS employees (" +
                "id SERIAL PRIMARY KEY," +
                "first_name VARCHAR," +
                "last_name VARCHAR," +
                "age INT," +
                "email VARCHAR," +
                "job_id INT REFERENCES jobs(id))";

        try (
                Connection connection = Config.getConnection();
                Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(sql);
            System.out.println("table employees created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addEmployee(Employee employee) {
        String sql =
                "INSERT  INTO employees (first_name, last_name, age, email, job_id) VALUES (?, ?, ?, ?, ?)";
        try (
                Connection connection = Config.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setLong(5, employee.getJobId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("employee was added");
    }

    @Override
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS employees";
        try (
                Connection connection = Config.getConnection();
                Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("table was deleted");
    }

    @Override
    public void cleanTable() {
        String sql = "DELETE FROM employees";
        try (
                Connection connection = Config.getConnection();
                Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("table was cleared");
    }

    @Override
    public void updateEmployee(Long id, Employee employee) {
        String sql = "UPDATE employees " +
                "SET    first_name = ?," +
                "           last_name = ?," +
                "           age = ?," +
                "           email = ?," +
                "           job_id = ?" +
                "WHERE id = ?";
        try (
                Connection connection = Config.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setInt(5, employee.getJobId());
            preparedStatement.setLong(6, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("employee was updated");
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try (
                Connection connection = Config.getConnection();
                Statement statement = connection.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                employeeList.add(new Employee(
                                resultSet.getLong("id"),
                                resultSet.getString("first_name"),
                                resultSet.getString("last_name"),
                                resultSet.getInt("age"),
                                resultSet.getString("email"),
                                resultSet.getInt("job_id")
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employeeList;
    }

    @Override
    public Employee findByEmail(String email) {
        Employee employee = new Employee();
        String sql = "SELECT * FROM employees WHERE email = ?";
        try (
                Connection connection = Config.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getInt("job_id"));
            } else {
                throw new RuntimeException("employee with email" + email + "not found");
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employee;
    }

    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        String sql = "";

        return null;
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        List<Employee> employeeList = new ArrayList<>();
        String sql = "SELECT * FROM employees JOIN jobs j ON employees.job_id = j.id WHERE position = ?";
        try (
                Connection connection = Config.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, position);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                employeeList.add(new Employee(
                                resultSet.getLong("id"),
                                resultSet.getString("first_name"),
                                resultSet.getString("last_name"),
                                resultSet.getInt("age"),
                                resultSet.getString("email"),
                                resultSet.getInt("job_id")
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employeeList;
    }
}
