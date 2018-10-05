using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;

namespace oneM2MClient.Utils
{
    public class ObjectTreeBuilder : TreeBuilder<object>
    {
        protected new List<ObjectTreeBuilder> children;
        public ObjectTreeBuilder(string rootObjectName, object rootObjectValue) : this(rootObjectName, new List<object>() { rootObjectValue })
        { }
        public ObjectTreeBuilder(string rootObjectName, List<object> rootObjectValues) : base(rootObjectName, rootObjectValues)
        {
            this.children = new List<ObjectTreeBuilder>();
        }

        public T GetValue<T>()
        {
            if (value != null)
                return (T)value.FirstOrDefault(c => c.GetType().IsSubclassOf(typeof(T)) || c.GetType() == typeof(T));
            else
                return default(T);
        }

        public new ObjectTreeBuilder AddValue(object value)
        {
            this.value.Add(value);
            return this;
        }

        public ObjectTreeBuilder AddChild(ObjectTreeBuilder child)
        {
            child.Parent = this;
            this.children.Add(child);
            return this;
        }

        public new ObjectTreeBuilder AddChild(string name, params object[] value)
        {
            return (AddChild(name, value.ToList()));
        }

        public new ObjectTreeBuilder AddChild(string name, List<object> value)
        {
            var node = new ObjectTreeBuilder(name, value) { Parent = this };
            children.Add(node);
            return node;
        }

        public new ObjectTreeBuilder this[int i]
        {
            get { return children[i]; }
        }
       
        public new ObjectTreeBuilder this[string name]
        {
            get { return children.FirstOrDefault(s => s.Name == name); }
        }

        public new ReadOnlyCollection<ObjectTreeBuilder> Children => children.AsReadOnly();

        public new bool HasChild(string childName)
        {
            if (children == null || children.Count == 0)
                return false;
            else
            {
                var child = children.Find(c => c.Name == childName);
                if (child == null)
                    return false;
                else
                    return true;
            }
        }

        public new bool HasChildPath(string childPath)
        {
            if (string.IsNullOrEmpty(childPath))
                return false;

            if (children == null || children.Count == 0)
                return false;
            else
            {
                if (childPath.Contains("/"))
                {
                    string[] splittedPath = childPath.Split(new char[] { '/' }, StringSplitOptions.RemoveEmptyEntries);
                    if (!HasChild(splittedPath[0]))
                        return false;
                    else
                    {
                        var child = this[splittedPath[0]];
                        return (child.HasChildPath(string.Join("/", splittedPath.Skip(1))));
                    }
                }
                else
                    return HasChild(childPath);
            }
        }

        public new ObjectTreeBuilder GetChild(string childPath)
        {
            if (string.IsNullOrEmpty(childPath))
                return null;

            if (children == null || children.Count == 0)
                return null;
            else
            {
                ObjectTreeBuilder superChild = null;
                if (childPath.Contains("/"))
                {
                    string[] splittedPath = childPath.Split(new char[] { '/' }, StringSplitOptions.RemoveEmptyEntries);
                    if (!HasChild(splittedPath[0]))
                        return null;
                    else
                    {
                        var child = this[splittedPath[0]];
                        superChild = child.GetChild(string.Join("/", splittedPath.Skip(1)));
                    }
                }
                else
                    superChild = this[childPath];

                return superChild;
            }
        }

        public new bool HasChildren()
        {
            if (children == null)
                return false;
            else
            {
                if (children.Count == 0)
                    return false;
                else
                    return true;
            }
        }

       

        public new void Traverse(Action<List<object>> action)
        {
            action(Value);
            foreach (var child in children)
                child.Traverse(action);
        }
    }
    public class TreeBuilder<T>
    {
        protected List<T> value;
        protected List<TreeBuilder<T>> children;
        public TreeBuilder<T> Parent { get; protected set; }
        public List<T> Value { get { return value; } }
        public string Name { get; protected set; }

        public TreeBuilder(string name, T data) : this (name, new List<T>() { data })
        { }

        public TreeBuilder(string name, List<T> data)
        {
            this.Name = name;
            this.value = data;
            this.children = new List<TreeBuilder<T>>();
        }

        public ReadOnlyCollection<TreeBuilder<T>> Children
        {
            get { return children.AsReadOnly(); }
        }

        public TreeBuilder<T> AddValue(T value)
        {
            this.value.Add(value);
            return this;
        } 

        public TreeBuilder<T> this[int i]
        {
            get { return children[i]; }
        }

        public TreeBuilder<T> this[string name]
        {
            get { return children.FirstOrDefault(s => s.Name == name); }
        }

        public void Traverse(Action<List<T>> action)
        {
            action(Value);
            foreach (var child in children)
                child.Traverse(action);
        }

        #region Child-Operations
        public TreeBuilder<T> AddChild(TreeBuilder<T> child)
        {
            child.Parent = this;
            this.children.Add(child);
            return this;
        }

        public TreeBuilder<T> AddChild(string name, params T[] value)
        {
            return (AddChild(name, value.ToList()));
        }

        public TreeBuilder<T> AddChild(string name, List<T> value)
        {
            var node = new TreeBuilder<T>(name, value) { Parent = this };
            children.Add(node);
            return node;
        }
        public bool HasChildPath(string childPath)
        {
            if (string.IsNullOrEmpty(childPath))
                return false;

            if (children == null || children.Count == 0)
                return false;
            else
            {
                if (childPath.Contains("/"))
                {
                    string[] splittedPath = childPath.Split(new char[] { '/' }, StringSplitOptions.RemoveEmptyEntries);
                    if (!HasChild(splittedPath[0]))
                        return false;
                    else
                    {
                        var child = this[splittedPath[0]];
                        return (child.HasChildPath(string.Join("/", splittedPath.Skip(1))));
                    }
                }
                else
                    return HasChild(childPath);
            }
        }
        public bool HasChildren()
        {
            if (children == null)
                return false;
            else
            {
                if (children.Count == 0)
                    return false;
                else
                    return true;
            }
        }
        public bool HasChild(string childName)
        {
            if (children == null || children.Count == 0)
                return false;
            else
            {
                var child = children.Find(c => c.Name == childName);
                if (child == null)
                    return false;
                else
                    return true;
            }
        }

        public TreeBuilder<T> GetChild(string childPath)
        {
            if (string.IsNullOrEmpty(childPath))
                return null;

            if (children == null || children.Count == 0)
                return null;
            else
            {
                TreeBuilder<T> superChild = null;
                if (childPath.Contains("/"))
                {
                    string[] splittedPath = childPath.Split(new char[] { '/' }, StringSplitOptions.RemoveEmptyEntries);
                    if (!HasChild(splittedPath[0]))
                        return null;
                    else
                    {
                        var child = this[splittedPath[0]];
                        superChild = child.GetChild(string.Join("/", splittedPath.Skip(1)));
                    }
                }
                else
                    superChild = this[childPath];

                return superChild;
            }
        }
        #endregion
    }
}
