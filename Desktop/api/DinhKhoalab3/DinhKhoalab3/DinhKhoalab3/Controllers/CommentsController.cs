using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Threading.Tasks;
using DinhKhoalab3.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;

namespace DinhKhoalab3.Controllers
{
    public class CommentsController : Controller
    {
        public IConfiguration Configuration { get; }

        public CommentsController(IConfiguration configuration)
        {
            Configuration = configuration;
        }
        public IActionResult Index(string id)
        {
            List<Comments> commentList = new List<Comments>();

            string connectionString = Configuration["ConnectionStrings:DefaultConnection"];
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                //SqlDataReader
                connection.Open();

                string sql = $"Select * From AspNetUsers ";
                SqlCommand command = new SqlCommand(sql, connection);

                using (SqlDataReader dataReader = command.ExecuteReader())
                {
                    while (dataReader.Read())
                    {
                        Comments comments = new Comments();
                        comments.Id = Convert.ToString(dataReader["Id"]);
                        comments.Name = Convert.ToString(dataReader["Name"]);
                        comments.Program = Convert.ToString(dataReader["Program"]);
                        comments.StudentComments = Convert.ToString(dataReader["StudentComments"]);

                        commentList.Add(comments);
                    }
                }

                connection.Close();
            }
            return View(commentList);
        }

        public IActionResult Create()
        {
            return View();
        }

        [HttpPost]
        public IActionResult Create(Comments comments)
        {
            if (ModelState.IsValid)
            {
                string connectionString = Configuration["ConnectionStrings:DefaultConnection"];
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    string sql = $"Insert Into AspNetUsers (Name, Program, StudentComments) Values ('{comments.Name}','{comments.Program}','{comments.StudentComments}')";

                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        command.CommandType = CommandType.Text;

                        connection.Open();
                        command.ExecuteNonQuery();
                        connection.Close();
                    }
                    return RedirectToAction("Index");
                }
            }
            else
                return View();
        }

        public IActionResult Update(int id)
        {
            string connectionString = Configuration["ConnectionStrings:DefaultConnection"];

            Comments comments = new Comments();
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                string sql = $"Select * From AspNetUsers Where Id='{id}'";
                SqlCommand command = new SqlCommand(sql, connection);

                connection.Open();

                using (SqlDataReader dataReader = command.ExecuteReader())
                {
                    while (dataReader.Read())
                    {
                        comments.Id = Convert.ToString(dataReader["Id"]);
                        comments.Name = Convert.ToString(dataReader["Name"]);
                        comments.Program = Convert.ToString(dataReader["Program"]);
                        comments.StudentComments = Convert.ToString(dataReader["StudentComments"]);
                    }
                }

                connection.Close();
            }
            return View(comments);
        }

        [HttpPost]
        [ActionName("Update")]
        public IActionResult Update_Post(Comments comments)
        {
            string connectionString = Configuration["ConnectionStrings:DefaultConnection"];
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                string sql = $"Update AspNetUsers SET Name='{comments.Name}', Program='{comments.Program}', StudentComments='{comments.StudentComments}' Where Id='{comments.Id}'";
                using (SqlCommand command = new SqlCommand(sql, connection))
                {
                    connection.Open();
                    command.ExecuteNonQuery();
                    connection.Close();
                }
            }

            return RedirectToAction("Index");
        }

        [HttpPost]
        public IActionResult Delete(int id)
        {
            string connectionString = Configuration["ConnectionStrings:DefaultConnection"];
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                string sql = $"Delete From AspNetUsers Where Id='{id}'";
                using (SqlCommand command = new SqlCommand(sql, connection))
                {
                    connection.Open();
                    try
                    {
                        command.ExecuteNonQuery();
                    }
                    catch (SqlException ex)
                    {
                        ViewBag.Result = "Operation got error:" + ex.Message;
                    }
                    connection.Close();
                }
            }

            return RedirectToAction("Index");
        }
    }
}

    