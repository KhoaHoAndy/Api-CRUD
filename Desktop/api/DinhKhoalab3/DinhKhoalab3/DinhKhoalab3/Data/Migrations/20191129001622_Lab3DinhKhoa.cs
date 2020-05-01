using Microsoft.EntityFrameworkCore.Migrations;

namespace DinhKhoalab3.Data.Migrations
{
    public partial class Lab3DinhKhoa : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "StudentComments",
                table: "AspNetUsers",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "StudentComments",
                table: "AspNetUsers");
        }
    }
}
