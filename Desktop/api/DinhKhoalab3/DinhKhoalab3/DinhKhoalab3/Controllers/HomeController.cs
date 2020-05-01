using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.AspNetCore.Authorization;
using DinhKhoalab3.Models;
using DinhKhoalab3.Data;
using Microsoft.AspNetCore.Identity;
using Microsoft.Extensions.Logging;
using Microsoft.EntityFrameworkCore;

namespace DinhKhoalab3.Controllers
{
    [Authorize]
    public class HomeController : Controller
    {

        public IConfiguration Configuration { get; }


        public HomeController(IConfiguration configuration)
        {
             Configuration = configuration;
        }

        public IActionResult Index(string id)
        {
            List<Teacher> teacherList = new List<Teacher>();

            string connectionString = Configuration["ConnectionStrings:DefaultConnection"];
            using (SqlConnection connection = new SqlConnection(connectionString))

            {
                //SqlDataReader
                connection.Open();

                string sql = $"Select * From AspNetUsers  Where Id='{id}' ";
                SqlCommand command = new SqlCommand(sql, connection);

                using (SqlDataReader dataReader = command.ExecuteReader())
                {
                    while (dataReader.Read())
                    {
                        Teacher teacher = new Teacher();
                        teacher.Id = Convert.ToString(dataReader["Id"]);
                        teacher.Name = Convert.ToString(dataReader["Name"]);
                        teacher.Skills = Convert.ToString(dataReader["Skills"]);
                        teacher.PhoneNumber = Convert.ToString(dataReader["PhoneNumber"]);
                        teacher.Address = Convert.ToString(dataReader["Address"]);
                        teacher.Languages = Convert.ToString(dataReader["Languages"]);
                        teacher.Education = Convert.ToString(dataReader["Education"]);
                        teacher.Experience = Convert.ToString(dataReader["Experience"]);
                        teacher.PhoneNumber = Convert.ToString(dataReader["PhoneNumber"]);
                        teacher.Prefertime = Convert.ToString(dataReader["Prefertime"]);

                        teacherList.Add(teacher);
                    }
                }

                connection.Close();
            }
            return View(teacherList);
        }
        

        public IActionResult Update(string id)
        {
            string connectionString = Configuration["ConnectionStrings:DefaultConnection"];

            Teacher teacher = new Teacher();
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                string sql = $"Select * From AspNetUsers Where Id='{id}'";
                SqlCommand command = new SqlCommand(sql, connection);

                connection.Open();

                using (SqlDataReader dataReader = command.ExecuteReader())
                {
                    while (dataReader.Read())
                    {
                        teacher.Id = Convert.ToString(dataReader["Id"]);
                        teacher.Name = Convert.ToString(dataReader["Name"]);
                        teacher.Skills = Convert.ToString(dataReader["Skills"]);
                        teacher.PhoneNumber = Convert.ToString(dataReader["PhoneNumber"]);

                    }
                }

                connection.Close();
            }
            return View(teacher);
        }

        [HttpPost]
        [ActionName("Update")]
        public IActionResult Update_Post(Teacher teacher)
        {
            string connectionString = Configuration["ConnectionStrings:DefaultConnection"];
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                string sql = $"Update AspNetUsers SET Name='{teacher.Name}', Skills='{teacher.Skills}', PhoneNumber='{teacher.PhoneNumber}'  Where Id='{teacher.Id}'";
                using (SqlCommand command = new SqlCommand(sql, connection))
                {
                    connection.Open();
                    command.ExecuteNonQuery();
                    connection.Close();
                }
            }

            return RedirectToAction("Index", "Home", new { id = teacher.Id });
        }

        public IActionResult Resume(string id)
        {
            string connectionString = Configuration["ConnectionStrings:DefaultConnection"];

            Teacher teacher = new Teacher();
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                string sql = $"Select * From AspNetUsers Where Id='{id}'";
                SqlCommand command = new SqlCommand(sql, connection);

                connection.Open();

                using (SqlDataReader dataReader = command.ExecuteReader())
                {
                    while (dataReader.Read())
                    {
                        teacher.Name = Convert.ToString(dataReader["Name"]);
                        teacher.Address = Convert.ToString(dataReader["Address"]);
                        teacher.Languages = Convert.ToString(dataReader["Languages"]);
                        teacher.Education = Convert.ToString(dataReader["Education"]);
                        teacher.Experience = Convert.ToString(dataReader["Experience"]);
                        teacher.PhoneNumber = Convert.ToString(dataReader["PhoneNumber"]);
                        teacher.Prefertime = Convert.ToString(dataReader["Prefertime"]);
                    }
                }

                connection.Close();
            }
            return View(teacher);
        }
        [HttpPost]
        [ActionName("Resume")]
        public IActionResult Resume_Post(Teacher teacher)
        {
            string connectionString = Configuration["ConnectionStrings:DefaultConnection"];
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                string sql = $"Update AspNetUsers SET Name='{teacher.Name}',Languages='{teacher.Languages}',Address='{teacher.Address}',Education='{teacher.Education}',Experience='{teacher.Experience}', Prefertime='{teacher.Prefertime}', PhoneNumber='{teacher.PhoneNumber}'  Where Id='{teacher.Id}'";
                using (SqlCommand command = new SqlCommand(sql, connection))
                {
                    connection.Open();
                    command.ExecuteNonQuery();
                    connection.Close();
                }
            }

            return RedirectToAction("Index", "Home", new { id = teacher.Id });
        }
    }
}
