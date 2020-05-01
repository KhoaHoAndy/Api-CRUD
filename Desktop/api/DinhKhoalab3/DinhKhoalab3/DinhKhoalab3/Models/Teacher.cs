using Microsoft.AspNetCore.Identity;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace DinhKhoalab3.Models
{
    public class Teacher 
    {
        //override
        public string Id { get; set; }

        [Required]
        public string Name { get; set; }

        [Required]

        public string Skills { get; set; }

        [Required]
        //override
        public string PhoneNumber { get; set; }

        [Required]
        public string Address { get; set; }

        [Required]
        public string Languages { get; set; }

        [Required]
        public string Education { get; set; }

        [Required]
        public string Experience { get; set; }

        [Required]
        public string Prefertime { get; set; }
    }
}
